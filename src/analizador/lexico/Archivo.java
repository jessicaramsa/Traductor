package analizador.lexico;

import java.io.*;
import javax.swing.JFileChooser;

public class Archivo {
    public String abrirLeerArch() {
        File f;
        JFileChooser j = new JFileChooser("src/test/");
        j.showOpenDialog(j);
        String path = j.getSelectedFile().getAbsolutePath();
        String lectura = "";
        f = new File(path);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String aux;
            while ((aux = br.readLine()) != null) {
                lectura = lectura + aux + " ";
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return lectura;
    }

    public void guardarArch(String nameArch, String linea) {
        BufferedWriter bw = null;

        try {
            File file = new File(nameArch);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(linea);
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error al cerrar. " + ioe);
            }
        }
    }
}
