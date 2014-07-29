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
            int substrate = 1024;
            do {
                int flow = 100;
                do{
                    workspace.command("set flow "+Integer.toString(flow));
                    workspace.command("set resourcein "+Integer.toString(substrate));
                    workspace.command("setup");
                    int i =0;
                    do {
                        workspace.command("go");
                        turtles = (Double) workspace.report("count turtles");
                        i++;
                    }while((i<3000)&&(turtles>0));
                    if(turtles >0){
                        System.out.println(substrate+"\t"+flow+"\t"+turtles);
                        flow = 0;
                    }
                    flow--;
                    //System.out.println(substrate+"\t"+flow+"\t"+turtles);
                }while(flow>0);
                substrate = substrate/2;
            }while (substrate>0);
            workspace.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println(turtles);
    }
}
