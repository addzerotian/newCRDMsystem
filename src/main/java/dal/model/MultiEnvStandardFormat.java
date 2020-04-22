package dal.model;

public class MultiEnvStandardFormat {
    private static MultiEnvStandardFormat instance = new MultiEnvStandardFormat();
    public static final String FILE_SEPARATOR_WINDOWS = "\\";
    public static final String FILE_SEPARATOR_LINUX = "/";
    private String fileSeparator;

    public MultiEnvStandardFormat() {
        //部署前检查部署环境，选择正确的文件分隔符
        changeToWindowsFileSeparator();
    }

    public static MultiEnvStandardFormat getInstance() { return instance; }

    public void changeToWindowsFileSeparator() { this.fileSeparator = FILE_SEPARATOR_WINDOWS; }

    public void changeToLinuxFileSeparator() { this.fileSeparator = FILE_SEPARATOR_LINUX; }

    public String getFileSeparator() {
        return fileSeparator;
    }
}
