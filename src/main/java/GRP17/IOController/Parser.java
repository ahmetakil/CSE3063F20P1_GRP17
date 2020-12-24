package GRP17.IOController;

public abstract class Parser {
     /*
     Custom interface for all the Parsing mechanisms.
      */
    protected String fileName;

    Parser(String fileName){
        this.fileName = fileName;
    }

    abstract Object parse();
}
