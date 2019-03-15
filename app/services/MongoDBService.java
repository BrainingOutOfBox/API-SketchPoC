package services;

import com.mongodb.Block;
import com.mongodb.ConnectionString;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.async.client.gridfs.*;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.mongodb.async.client.gridfs.helpers.AsyncStreamHelper.toAsyncInputStream;
import static com.mongodb.async.client.gridfs.helpers.AsynchronousChannelHelper.channelToOutputStream;
import static com.mongodb.client.model.Filters.eq;

public class MongoDBService {

    MongoClient client;
    MongoDatabase database;
    GridFSBucket gridFSBucket;

    public MongoDBService() {
        client = MongoClients.create(new ConnectionString("mongodb://elias:Methode635@localhost:27017/?authSource=admin&authMechanism=SCRAM-SHA-1"));
        database = client.getDatabase("PoCDB");
        gridFSBucket = GridFSBuckets.create(database);
    }

    public String uploadFile(File file) throws IOException, ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        String fileName = file.getName();

        // Get the input stream
        final AsyncInputStream streamToUploadFrom = toAsyncInputStream(Files.readAllBytes(file.toPath()));

        // Create some custom options
        GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(1024 * 1024).metadata(new Document("contentType", "image/jpg"));

        gridFSBucket.uploadFromStream(fileName, streamToUploadFrom, options,
                new SingleResultCallback<ObjectId>() {
                    @Override
                    public void onResult(final ObjectId result, final Throwable t) {
                        System.out.println("The fileId of the uploaded file is: " + result.toHexString());
                        future.complete(result.toHexString());

                        streamToUploadFrom.close(new SingleResultCallback<Void>() {
                            @Override
                            public void onResult(final Void result, final Throwable t) {
                                // Stream closed

                            }
                        });

                    }
                }
        );

        return future.get();

    }

    public String uploadFileAsStream(byte[] file, String fileName) throws ExecutionException, InterruptedException {
        ByteBuffer data = ByteBuffer.wrap(file);
        CompletableFuture<String> future = new CompletableFuture<>();

        final GridFSUploadStream uploadStream = gridFSBucket.openUploadStream(fileName);
        uploadStream.write(data, new SingleResultCallback<Integer>() {
            @Override
            public void onResult(Integer result, Throwable t) {
                System.out.println("The fileId of the uploaded file is:" + uploadStream.getObjectId().toHexString());
                future.complete(uploadStream.getObjectId().toHexString());

                uploadStream.close(new SingleResultCallback<Void>() {
                    @Override
                    public void onResult(Void result, Throwable t) {
                        // stream close
                    }
                });
            }
        });

        return future.get();
    }

    public void findFileByName(String name){

        gridFSBucket.find(eq("filename", name)).forEach(
                new Block<GridFSFile>() {
                    @Override
                    public void apply(final GridFSFile gridFSFile) {
                        System.out.println(gridFSFile.getFilename() + " has file id: " + gridFSFile.getObjectId().toHexString());

                    }
                },
                new SingleResultCallback<Void>() {
                    @Override
                    public void onResult(final Void result, final Throwable t) {
                        // Finished
                    }
                }
        );

    }

    public File downloadFile(String id) throws IOException, ExecutionException, InterruptedException {
        ObjectId fileId = new ObjectId(id);
        Path outputPath = Paths.get("/Users/elias/Documents/Methode635/PoC-BA/tmp/Temp.jpg");

        CompletableFuture<File> future = new CompletableFuture<>();

        AsynchronousFileChannel streamToDownloadTo = AsynchronousFileChannel.open(outputPath,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE);

        gridFSBucket.downloadToStream(fileId, channelToOutputStream(streamToDownloadTo),
                new SingleResultCallback<Long>() {
                    @Override
                    public void onResult(final Long result, final Throwable t) {
                        System.out.println("downloaded file sized: " + result);
                        future.complete(outputPath.toFile());

                        try {
                            streamToDownloadTo.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
        
        return future.get();
    }

    public byte[] downloadFileAsStream(String id) throws ExecutionException, InterruptedException {
        ObjectId fileId = new ObjectId(id);
        final ByteBuffer dstByteBuffer = ByteBuffer.allocate(1024 * 1024);
        final GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(fileId);
        CompletableFuture<byte[]> future = new CompletableFuture<>();

        downloadStream.read(dstByteBuffer, new SingleResultCallback<Integer>() {
            @Override
            public void onResult(final Integer result, Throwable t) {
               dstByteBuffer.flip();
               byte[] bytes = new byte[result];
               dstByteBuffer.get(bytes);
               System.out.println("downloaded file sized: " + result);
               future.complete(bytes);

               downloadStream.close(new SingleResultCallback<Void>() {
                   @Override
                   public void onResult(Void result, Throwable t) {
                       // stream closed
                   }
               });
            }
        });

        return future.get();
    }

}
