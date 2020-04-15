package dal.model;

public class MultiEnvStandardFormat {
    private static MultiEnvStandardFormat instance = new MultiEnvStandardFormat();
    public static final String FILE_SEPARATOR_WINDOWS = "\\";
    public static final String FILE_SEPARATOR_LINUX = "/";
    private String fileSeparator;

    public MultiEnvStandardFormat() {
        fileSeparator = FILE_SEPARATOR_LINUX;
    }

    public static MultiEnvStandardFormat getInstance() { return instance; }

    public void changeToWindowsFileSeparator() { this.fileSeparator = FILE_SEPARATOR_WINDOWS; }

    public void changeToLinuxFileSeparator() { this.fileSeparator = FILE_SEPARATOR_LINUX; }

    public String getFileSeparator() {
        return fileSeparator;
    }
}
