package dto;
public enum FileType {
    CSV("csv"),
    PNG("png"),
    JSON("json");

    private final String fileTypeName;

    FileType(String fileType) {
        fileTypeName = fileType;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }
}
