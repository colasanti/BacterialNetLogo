/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bacterialnetlogo;

/**
 *
 * @author ric
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.nlogo.headless.HeadlessWorkspace;

public class BacterialNetLogo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File outFile = new File("data/newMOnodModel050814.cvs");

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(outFile);

            BufferedWriter out = new BufferedWriter(fileWriter);
            HeadlessWorkspace workspace
                    = HeadlessWorkspace.newInstance();
            System.out.println("Working Directory = "
                    + System.getProperty("user.dir"));
            double turtles = 0.0;
            out.write("Substrate\tflow\tturtles");
            try {
                workspace.open(
                        "src/bacterialnetlogo/Chemostat.nlogo");
                int substrate = 250;
                do {
                    int flow = 100;
                    do {
                        workspace.command("set flow " + Integer.toString(flow));
                        workspace.command("set resourcein " + Integer.toString(substrate));
                        workspace.command("set model \"Monod\" ");
                        workspace.command("setup");
                        int i = 0;
                        do {
                            workspace.command("go");
                            turtles = (Double) workspace.report("count turtles");
                            i++;
                        } while ((i < 3000) && (turtles > 0));
                        if (turtles > 0) {
                            out.write(substrate + "\t" + flow + "\t" + turtles+"\n");
                            System.out.println(substrate +"\t" + flow + "\t" + turtles);
                            flow = 0;
                        }
                        flow--;
                        //System.out.println(substrate+"\t"+flow+"\t"+turtles);
                    } while (flow > 0);
                    substrate -= 5;
                } while (substrate > 0);
                workspace.dispose();
                out.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("done");
        } catch (IOException ex) {
            Logger.getLogger(BacterialNetLogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
