import javax.swing.*;
import java.awt.*;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExternalStorageCheckerGUI {
    private JFrame frame;
    private JTextArea textArea;
    ExternalStorageChecker ext = new ExternalStorageChecker();

    public ExternalStorageCheckerGUI() {
        frame = new JFrame("External Storage Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Set background color of JFrame
        frame.getContentPane().setBackground(Color.BLACK);

        textArea = new JTextArea();
        textArea.setEditable(false);

        // Set font color of JTextArea
        textArea.setForeground(Color.YELLOW);

        // Set background color of JTextArea
        textArea.setBackground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(textArea);

        // Set background color of JScrollPane
        scrollPane.getViewport().setBackground(Color.BLACK);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public void display() {
        frame.setVisible(true);
    }

    public void checkForExternalStorage() {
        StringBuilder result = new StringBuilder();

        Iterable<Path> roots = FileSystems.getDefault().getRootDirectories();

        for (Path root : roots) {
            try {
                FileStore store = Files.getFileStore(root);
                String type = store.type();

                // Append the details to the result string
                result.append("File system: ").append(root).append("\n");
                result.append("  Type: ").append(type).append("\n");
                result.append("  Total space: ").append(store.getTotalSpace()).append("\n");
                result.append("  Usable space: ").append(store.getUsableSpace()).append("\n");
                result.append("  Unallocated space: ").append(store.getUnallocatedSpace()).append("\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        textArea.setText(result.toString());
    }

    public static void main(String[] args) {
        ExternalStorageCheckerGUI gui = new ExternalStorageCheckerGUI();

        ExternalStorageMonitor obj = new ExternalStorageMonitor();
        obj.checkForExternalStorage();
        obj.sessionDetails();
        gui.display();
        gui.checkForExternalStorage();
    }
}
