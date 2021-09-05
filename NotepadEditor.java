
package notepadeditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;


public class NotepadEditor implements ActionListener {
       JMenuItem neww,open,save,saveAs,cut,copy,paste,font,font_color,background_color;
       JTextArea textarea;
       JFrame jf,font_frame;
       JButton ok;
       JComboBox font_family,font_size,font_style;
      File file;
    NotepadEditor()
    {
        
        try
        {
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName()); 
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } 
       
       jf=new JFrame("*Untitled*-Notepad");
       jf.setSize(750,700);
       jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       jf.setLocationRelativeTo(null);
    
    JMenuBar jmb=new JMenuBar();
    
       JMenu file=new JMenu("File");
       
        neww=new JMenuItem("New");
        neww.addActionListener(this);
       file.add(neww);
       
       open=new JMenuItem("Open");
       open.addActionListener(this);
       file.add(open);
       
       save=new JMenuItem("save");
       save.addActionListener(this);
       file.add(save);
       
       saveAs=new JMenuItem("SaveAs");
       saveAs.addActionListener(this);
       file.add(saveAs);
       
       jmb.add(file);
       
       JMenu edit=new JMenu("Edit");
       
       cut=new JMenuItem("Cut");
       cut.addActionListener(this);
       edit.add(cut);
       
       copy=new JMenuItem("Copy");
       copy.addActionListener(this);
       edit.add(copy);
       
       paste=new JMenuItem("Paste");
       paste.addActionListener(this);
       edit.add(paste);
        jmb.add(edit);
        
       JMenu format=new JMenu("Format");
       
       font=new JMenuItem("Font");
       font.addActionListener(this);
       format.add(font);
       
       font_color=new JMenuItem("Font_Color");
       font_color.addActionListener(this);
       format.add(font_color);
       
       background_color=new JMenuItem("Bckground Color");
       background_color.addActionListener(this);
       format.add(background_color);
       
       jmb.add(format);
      
       jf.setJMenuBar(jmb);
       
       textarea=new JTextArea();
       
       JScrollPane scrollpane=new JScrollPane(textarea);
       scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
       scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       
       jf.add(scrollpane);
 
       
       
    jf.setVisible(true);  
    }
    
    public static void main(String[] args) {
       new NotepadEditor();
    
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==neww)
        {
           newFile();
        }
        if(e.getSource()==open)
        {
           openFile();
        }
        if(e.getSource()==save)
        {
            saveFile();
        }
        if(e.getSource()==saveAs)
        {
            
        saveAsFile();
        }
        if(e.getSource()==cut)
        {
            textarea.cut();
        }
        if(e.getSource()==copy)
        {
            textarea.copy();
        }
        if(e.getSource()==paste)
        {
            textarea.paste();
        }
        if(e.getSource()==font) 
        {
            openFontFrame();
        }
        if(e.getSource()==ok)
        {
           setFontOnTextarea();
        }
        if(e.getSource()==font_color)
        {
          Color c=JColorChooser.showDialog(jf, "choose font color", Color.black);
            textarea.setForeground(c);
        }
        if(e.getSource()==background_color)
        {
            Color c=JColorChooser.showDialog(jf,"choose background color", Color.white);
            textarea.setBackground(c);
        }
        
    }
    
    void openFontFrame()
    {
      font_frame=new JFrame("choose font");
      font_frame.setSize(500, 550);
      font_frame.setLocationRelativeTo(jf);
      font_frame.setLayout(null);
      
      String fonts[] = 
      GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
      font_family=new JComboBox(fonts);
      font_family.setBounds(50,100,100,30);
      font_frame.add(font_family);
      
      String[] sizes={"10","12","14","16","18","20","22","24","26","28","30","32","34","36","38","40"};
      font_size=new JComboBox(sizes);
      font_size.setBounds(180,100,100,30);
      font_frame.add(font_size);
      
      String style[]={"PLAIN","BOLD","ITALLIC"};
      font_style=new JComboBox(style);
      font_style.setBounds(310,100,100,30);
      font_frame.add(font_style);
      
      ok=new JButton("OK");
      ok.setBounds(370,430,100,50);
      ok.addActionListener(this);
      font_frame.add(ok);
      
      font_frame.setVisible(true);
    }
    
    void setFontOnTextarea()
    {
         String fontfamily=(String)font_family.getSelectedItem();
            String fontsize=(String)font_size.getSelectedItem(); //10,20,30
            String fontstyle=(String)font_style.getSelectedItem(); //plain,bold,itallic
            
            int style=0;
            if(fontstyle.equals("PLAIN"))
            {
                style=0;
            }
           else if(fontstyle.equals("BOLD"))
           {
               style=1;
           }
           else if(fontstyle.equals("ITALLIC"))
           {
               style=2;
           }
            
            
           Font fontt=new Font(fontfamily,style ,Integer.parseInt(fontsize));
           textarea.setFont(fontt);
           
           font_frame.setVisible(false);
    }
    
    
    void saveFile()
   {
        String title=jf.getTitle();
      
        if(title.equals("*Untitled*-Notepad"))
        {
            saveAsFile();
        }
        else
        {
            String text=textarea.getText();
            try(FileOutputStream fos=new FileOutputStream(file))
                {
                byte[] b=text.getBytes();
                fos.write(b);
                
                }
                catch(Exception ee)
                {
                    ee.printStackTrace();
                }
        }
            
    }
    void openFile()
    {
        JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(jf);
            
            if(result==0)
            {
                textarea.setText("");
                 file=fileChooser.getSelectedFile();
        jf.setTitle(file.getName());
        
             try(FileInputStream fis=new FileInputStream(file);)
             {
               int i;
               while((i=fis.read()) !=-1)
                 {
                  textarea.append(String.valueOf((char)i));
                 }

            }
            catch(IOException ae)
            {
                  ae.printStackTrace();
            }
             
         } 
    }
    void saveAsFile()
    {
        JFileChooser fileChooser=new JFileChooser();
            int result =fileChooser.showSaveDialog(jf);
            if(result==0)
            {
                String text=textarea.getText();
                file=fileChooser.getSelectedFile();
                jf.setTitle(file.getName());
                try(FileOutputStream fos=new FileOutputStream(file))
                {
                byte[] b=text.getBytes();
                fos.write(b);
                
                }
                catch(Exception ee)
                {
                    ee.printStackTrace();
                }
        }
    }
    void newFile()
    {
        
            String text=textarea.getText();
            if(!text.equals(""))
            {
               int i= JOptionPane.showConfirmDialog(jf, "do you want to save this file?");
               if(i==0)
               {
                   saveAsFile();
                   textarea.setText("");
                   jf.setTitle("*Untitled*-Notepad");
               }
              else if(i==1)
               {
                   textarea.setText("");
               }
            
            
            }
    }
}
