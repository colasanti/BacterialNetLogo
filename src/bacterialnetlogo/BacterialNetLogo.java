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
import org.nlogo.headless.HeadlessWorkspace;

public class BacterialNetLogo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HeadlessWorkspace workspace
                = HeadlessWorkspace.newInstance();
        System.out.println("Working Directory = "
                + System.getProperty("user.dir"));
        double turtles = 0.0;
        System.out.println("Substrate\tflow\tturtles");
        try {
            workspace.open(
                    "src/bacterialnetlogo/Chemostat.nlogo");
            for (int substrate = 1024; substrate >= 1; substrate = substrate / 2) {
                for (int flow = 1; flow <= 100; flow++) {
                    workspace.command("set flow "+Integer.toString(flow));
                    workspace.command("set resourcein "+Integer.toString(substrate));
                    workspace.command("setup");
                    for (int i = 0; i < 500; i++) {
                        workspace.command("go");
                        turtles = (Double) workspace.report("count turtles");
                    }
                    System.out.println(substrate+"\t"+flow+"\t"+turtles);
                }
            }
            workspace.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(turtles);
    }
}
