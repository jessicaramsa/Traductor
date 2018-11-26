package archivo;

import estructuras.lista.Lista;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;

public class ManejadorArchivos {
    Lista archivo = new Lista();
    
    public File abrir() {
        JFileChooser fch = new JFileChooser("src/test");
        fch.showOpenDialog(fch);
        if (fch.isFileSelectionEnabled()) {
            String path = fch.getSelectedFile().getAbsolutePath();
            File f = new File(path);
            return f;
        }
        return null;
    }
    
    public Lista leer(File arch) {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = new FileInputStream(arch);
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String l = br.readLine();
            if (l != null) {
                while (l != null) {
                    archivo.insertarF(l);
                    l = br.readLine();
                }
                return archivo;
            } else return null;
        } catch (IOException e) {
            System.out.println("Error al leer. " + e);
            return null;
        } finally {
            try {
                is.close();
                isr.close();
                br.close();
            } catch (IOException e) { System.out.println("Error cerrar. " + e); }
        }
    }
}
