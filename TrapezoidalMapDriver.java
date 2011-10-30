import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TrapezoidalMapDriver implements ActionListener{
	
  	private JPanel buttonPanel = new JPanel(new GridLayout(0,1));
  	private TrapezoidPanel draw = new TrapezoidPanel();
  	private JButton clear = new JButton("Clear");
    private JButton clearAll = new JButton("Clear All");
//  private JButton naive = new JButton("Naive");
    private JButton incremental = new JButton("Incremental");
    private JFrame frame = new JFrame("Trapezoidal Map Project");

    public TrapezoidalMapDriver() {
        this.createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        buttonPanel = new JPanel(new GridLayout(0, 1));
       
        draw.setPreferredSize(new Dimension(500, 500));

        buttonPanel.add(clear,BorderLayout.LINE_END);
        buttonPanel.add(clearAll,BorderLayout.LINE_END);
//      buttonPanel.add(naive,BorderLayout.LINE_END);
        buttonPanel.add(incremental,BorderLayout.LINE_END);

        clear.addActionListener(this);
        clearAll.addActionListener(this);
//      naive.addActionListener(this);
        incremental.addActionListener(this);

        frame.getContentPane().add(buttonPanel,BorderLayout.LINE_END);
        frame.getContentPane().add(draw,BorderLayout.CENTER);
        
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
      if (e.getSource()==clear) {
        draw.clear(); 
      }
      else if(e.getSource()==clearAll) {
        draw.clearAll();
      }
/*      else if(e.getSource()==naive) {
        TrapezoidalMap t = new TrapezoidalMap(draw.getShapes());
        draw.setLines(t.naiveMap(draw.getHeight(),draw.getWidth()));
      }  
*/    else if(e.getSource()==incremental) {
        TrapezoidalMap t = new TrapezoidalMap(draw.getShapes());
        draw.setLines(t.naiveMap(draw.getHeight(),draw.getWidth()));
        draw.setFaces(t.incrementalMap(draw.getHeight(),draw.getWidth()));
        if(draw.queryPoint != null) t.retrievePoint(draw.queryPoint);
      }  
   }

	public static void main(String[] args) {
         javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              TrapezoidalMapDriver driver = new TrapezoidalMapDriver();
            }
        });
	}

}
