package dto;

import java.util.List;

public class RequestForm {
    private String manifest;
    private List<String> fileTypes;

    public String getManifest() {
        return manifest;
    }

    public void setManifest(String manifest) {
        this.manifest = manifest;
    }

    public List<String> getFileTypes() {
        return fileTypes;
    }

    public void setFileTypes(List<String> fileTypes) {
        this.fileTypes = fileTypes;
    }
}
