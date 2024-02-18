package internetStore.util;

public class ContextPath {
    public static int idFromPath(String path) throws NumberFormatException {
        String[] pathParts = path.split("/");
        if (pathParts.length != 2) {
            throw new NumberFormatException("Invalid path format");
        }
        return Integer.parseInt(pathParts[1]);
    }
}
