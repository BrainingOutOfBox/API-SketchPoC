package controllers;


import akka.stream.javadsl.FileIO;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import play.data.Form;
import play.http.HttpEntity;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.mvc.*;
import services.MongoDBService;
import views.html.index;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * This class uses a custom body parser to change the upload type.
 */
@Singleton
public class HomeController extends Controller {

    private final play.data.FormFactory formFactory;
    private MessagesApi messagesApi;
    private MongoDBService service;

    @Inject
    public HomeController(play.data.FormFactory formFactory, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;
        this.service = new MongoDBService();
    }

    public Result index(Http.Request request ) {
        Form<FormData> form = formFactory.form(FormData.class).bindFromRequest(request);
        Messages messages = this.messagesApi.preferred(request);
        return ok(index.render(form, request, messages));
    }

    /**
     * This method uses MyMultipartFormDataBodyParser as the body parser
     */
    @BodyParser.Of(MyMultipartFormDataBodyParser.class)
    public Result upload() throws IOException, ExecutionException, InterruptedException {
        final Http.MultipartFormData<File> formData = request().body().asMultipartFormData();
        final Http.MultipartFormData.FilePart<File> filePart = formData.getFile("name");
        final File file = filePart.getFile();
        String fileId = service.uploadFile(file);
        return ok("file id = " + fileId);
    }

    /**
     * This method uses MyMultipartFormDataBodyParser as the body parser
     */
    @BodyParser.Of(MyMultipartFormDataBodyParser.class)
    public Result uploadLargeData() throws IOException, ExecutionException, InterruptedException {
        final Http.MultipartFormData<File> formData = request().body().asMultipartFormData();
        final Http.MultipartFormData.FilePart<File> filePart = formData.getFile("name");
        final File file = filePart.getFile();
        final byte[] fileData = Files.readAllBytes(file.toPath());
        final String fileName = file.getName();
        String fileId = service.uploadFileAsStream(fileData, fileName);
        return ok("file id = " + fileId);
    }

    public Result findFileByName(String name){
        service.findFileByName(name);
        return ok();
    }

    public Result download(String id) throws IOException, ExecutionException, InterruptedException {
        File file = service.downloadFile(id);
        return ok(file, false);

    }

    public Result downloadLargeData(String id) throws InterruptedException, ExecutionException, IOException {
        File file = service.downloadFile(id);
        Path path = file.toPath();
        Source<ByteString, ?> source = FileIO.fromPath(path);

        Optional<Long> contentLength = Optional.of(file.length());

        return new Result(
                new ResponseHeader(200, Collections.emptyMap()),
                new HttpEntity.Streamed(source, contentLength, Optional.of("image/jpg"))
        );
    }

    public Result downloadLargeData2(String id) throws ExecutionException, InterruptedException {
        byte[] result = service.downloadFileAsStream(id);
        return ok(result);
    }


    public Result emptySite(){
        return ok();
    }

}

