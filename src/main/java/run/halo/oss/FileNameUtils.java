package run.halo.oss;

import cn.hutool.core.util.StrUtil;
import com.google.common.io.Files;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xirizhi
 */
public final class FileNameUtils {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private FileNameUtils() {
    }

    public static String removeFileExtension(String filename, boolean removeAllExtensions) {
        if (filename == null || filename.isEmpty()) {
            return filename;
        }
        var extPattern = "(?<!^)[.]" + (removeAllExtensions ? ".*" : "[^.]*$");
        return filename.replaceAll(extPattern, "");
    }

    /**
     * Append random string after file name.
     * <pre>
     * Case 1: halo.run -> halo-xyz.run
     * Case 2: .run -> xyz.run
     * Case 3: halo -> halo-xyz
     * </pre>
     *
     * @param filename is name of file.
     * @param length   is for generating random string with specific length.
     * @return File name with random string.
     */
    public static String randomFileName(String filename, int length) {
        var nameWithoutExt = Files.getNameWithoutExtension(filename);
        var ext = Files.getFileExtension(filename);
        var random = RandomStringUtils.randomAlphabetic(length).toLowerCase();
        if (StringUtils.isBlank(nameWithoutExt)) {
            return random + "." + ext;
        }
        if (StringUtils.isBlank(ext)) {
            return nameWithoutExt + "-" + random;
        }
        return nameWithoutExt + "-" + random + "." + ext;
    }

    /**
     * Change the file name to yyyyMMddHHMMss format.
     *
     * @param fileName is name of file.
     * @return File name with yyyyMMddHHMMss format.
     */
    public static String formatDateInFileName(String fileName,Boolean namePrefix) {
        var ext = Files.getFileExtension(fileName);
        var formattedDate = dateFormat.format(new Date());
        if (StringUtils.isBlank(ext)) {
            return formattedDate;
        }
        String mainFilename = "";
        if(namePrefix != null && namePrefix){
            mainFilename = Files.getNameWithoutExtension(fileName);
            if(mainFilename.length()>15){
                mainFilename = mainFilename.substring(0,15);
            }
            mainFilename = "-" +mainFilename;
        }
        return formattedDate + mainFilename + "." + ext;
    }

    public static String fileType(String fileName) {
        var ext = Files.getFileExtension(fileName);
        if (StrUtil.isBlank(ext)) {
            return "file";
        }
        if (isPicture(fileName)) {
            return "image/" + Files.getFileExtension(fileName);
        }
        return "file";
    }

    /**
     * Judge whether the file supports browsing picture display based on the file name suffix.
     *
     * @param fileName is name of file.
     * @return true if the file supports browsing picture display, false otherwise.
     */
    public static boolean isPicture(String fileName) {
        var ext = Files.getFileExtension(fileName);
        return ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("gif") || ext.equalsIgnoreCase("webp") || ext.equalsIgnoreCase("ico");
    }
}
