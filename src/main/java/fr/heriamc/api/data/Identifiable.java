package fr.heriamc.api.data;

public interface Identifiable<A> {

    A getIdentifier();
    void setIdentifier(A identifier);

}
