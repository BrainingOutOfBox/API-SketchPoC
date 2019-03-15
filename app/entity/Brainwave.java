package entity;

import java.util.ArrayList;

public final class Brainwave {
    private int nrOfBrainwave;
    ArrayList<Idea> ideas = new ArrayList<>();

    public Brainwave() {

    }

    public Brainwave(int nrOfBrainwave, ArrayList<Idea> ideas) {
        this.nrOfBrainwave = nrOfBrainwave;
        this.ideas = ideas;
    }

    public int getNrOfBrainwave() {
        return nrOfBrainwave;
    }

    public void setNrOfBrainwave(int nrOfBrainwave) {
        this.nrOfBrainwave = nrOfBrainwave;
    }

    public ArrayList<Idea> getIdeas() {
        return ideas;
    }

    public void setIdeas(ArrayList<Idea> ideas) {
        this.ideas = ideas;
    }

    public void addIdea(Idea idea){
        this.ideas.add(idea);
    }
}
