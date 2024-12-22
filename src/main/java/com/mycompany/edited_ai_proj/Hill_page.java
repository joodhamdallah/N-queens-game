/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.edited_ai_proj;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author hp
 */
public class Hill_page extends javax.swing.JFrame {
 public static int N =0;
  // private int[] queens;
  // private JLabel[] boardCells;
   
   public Hill_page(){
        initComponents();
         setTitle("Hill Climbing");
         jPanel2.setVisible(false);
    }

    public static void putrandom(int[][] ar, int[] s) {
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            s[i] = rand.nextInt(N);
            
           ar[s[i]][i] = 1;
          
        }
    }
    public static void displaymatrix(int[][] ar) {
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(ar[i][j] + " ");
            }
            System.out.println();
        }
         System.out.println();
    }
    public static void printState(int[] s) {
        for (int i = 0; i < N; i++) {
            System.out.println(s[i]);
        }
    }
   
    public static void zeroArray(int[][] board, int value) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = value;
            }
        }
        }
    
    public static boolean compareStates(int[] state1, int[] state2) {
        for (int i = 0; i < N; i++) {
            if (state1[i] != state2[i]) {
                return false;
            }
        }
        return true;
    }
    public static void generateBoard(int[][] board, int[] state) {
        zeroArray(board, 0);
        for (int i = 0; i < N; i++) {
            board[state[i]][i] = 1;
        }
     }
    
    
    public static void copyState(int[] state1, int[] state2) {
        for (int i = 0; i < N; i++) {
            state1[i] = state2[i];
         }
    }
    public static int find_heurstic(int[][] ar, int[] s) {
        int q_attacking = 0; //number of queens in the same row, column or diagonal attacking each other directly or indirectly
        int row, col;

        for (int i = 0; i < N; i++) {
            if (ar[s[i]][i] == 1) {
                row = s[i]; //take the row that has 1
                col = i - 1;   //check the column on the left 
                while (col >= 0) {           //to stay inside the N board, ( bcz the 1 maybe on the corner)
                    if (ar[row][col] == 1) {
                        q_attacking++; //if you find queen in the same row(from the left), increment the q_attacking
                    }
                    col--; //check for the indirect queens in the same row(left)
                }

                row = s[i];
                col = i + 1;          //check the column on the right 
                while (col < N) {     //the 1 maybe in  the last column of the matrix, so make sure to stay inside the  N*N board
                    if (ar[row][col] == 1) {
                        q_attacking++; ////if you find queen in the same row(from the right), increment the q_attacking
                    }
                    col++;    //check for the indirect queens in the same row(right)
                }

                row = s[i] - 1;   //take the row above the queen
                col = i - 1;   //take the coulmn on the left
                while (col >= 0 && row >= 0) {
                    if (ar[row][col] == 1) { //check the diagonal up from the left 
                        q_attacking++;
                    }
                    col--; // check for indirect queens
                    row--;
                }

                row = s[i] + 1;  //take the row below the queen
                col = i + 1;  //take the coulmn on the right
                while (col < N && row < N) {
                    if (ar[row][col] == 1) {  //check the diagonal down from the right 
                        q_attacking++;  // check for indirect queens
                    }
                    col++;
                    row++;
                }

                row = s[i] + 1;  //take the row below the queen
                col = i - 1;  //take the coulmn on the left
                while (col >= 0 && row < N) {  //check the diagonal down from the left
                    if (ar[row][col] == 1) {
                        q_attacking++;   // check for indirect queens
                    }
                    col--;
                    row++;
                }

                row = s[i] - 1;
                col = i + 1;
                while (col < N && row >= 0) {  //check the diagonal up from the right
                    if (ar[row][col] == 1) {   
                        q_attacking++;
                    }
                    col++;
                    row--;
                }
            }
        }

        return q_attacking / 2;
    }
    public static void getopChild(int[][] ar, int[] s) {
        int[][] opar = new int[N][N];
        int[] ops = new int[N];
        int e;

        copyState(ops, s);
        generateBoard(opar, ops);
        
      
        int h_current = find_heurstic(opar, ops);
        
       int[][] childar = new int[N][N];
        int[] childs = new int[N];

        copyState(childs, s);
        
        
        
        generateBoard(childar, childs);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (j != s[i]) {
                    childs[i] = j;
                    childar[childs[i]][i] = 1;
                    childar[s[i]][i] = 0;

                    int h_next = find_heurstic(childar, childs);

                    e = h_current - h_next;

                    if (e >= 0) {
                        h_current = h_next;
                      
                        copyState(ops, childs);
                        generateBoard(opar, ops);
                    }

                    childar[childs[i]][i] = 0;
                    childs[i] = s[i];
                    childar[s[i]][i] = 1;
                }
            }
        }

        copyState(s, ops);
        zeroArray(ar, 0);
        generateBoard(ar, s);
    }

    public static void hill_algo(int[][] board, int[] state) {
        
        int[][] neighbourBoard = new int[N][N]; 
        int[] neighbourState = new int[N];
        
        copyState(neighbourState, state);
        generateBoard(neighbourBoard, neighbourState);
     

        while (true) {
            copyState(state, neighbourState);
            generateBoard(board, state);

             getopChild(neighbourBoard, neighbourState);

            if (compareStates(state, neighbourState)) {
               displaymatrix(board);
                break;
            } else if (find_heurstic(board, state) <= find_heurstic(neighbourBoard, neighbourState)) {
                displaymatrix(board);
                break;}
        }
    }
    
    
    private static int[][] getrandomchild(int[][] ar,int[] s,int[] nexts) {
        int[][] childar = new int[N][N];
        int[] childs = new int[N];

        copyState(childs, s);
        generateBoard(childar, childs);
        copyState(nexts,s);
        
        Random rand = new Random();

        for (int i = 0; i < N; i++) {
            int j = rand.nextInt(N);
            childs[i] = j;
            childar[childs[i]][i] = 1;
            if(childs[i]!=s[i]){
            childar[s[i]][i] = 0;
            nexts[i]=childs[i];
            }
            
        }

        return childar;
    }
    
    
    
    
    public void draw(int[] ry) {
   // queens = new int[N];
    //boardCells = new JLabel[N * N];
   Color customColor = new Color(204, 0, 0);
   Color customColor2 = new Color(243,188,21);
       
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       Dimension preferredSize = new Dimension(260, 260);
       jPanel2.setPreferredSize(preferredSize);
       jPanel2.setLayout(new GridLayout(N, N));

        // Initialize board cells
        for (int i = 0; i < N ; i++) {
          for (int j = 0; j < N; j++) {
            JLabel cell = new JLabel();
            cell.setOpaque(true);
            cell.setHorizontalAlignment(SwingConstants.CENTER);
            cell.setBorder(BorderFactory.createLineBorder(customColor2));
            cell.setBackground(customColor);
            
            if(i%2==0 && j%2==0) {
                
            	cell.setBackground(customColor2);
            	cell.setBorder(BorderFactory.createLineBorder(customColor));
            }
            else if(!(i%2==0 || j%2==0)) {
        
            	cell.setBackground(customColor2);
            	cell.setBorder(BorderFactory.createLineBorder(customColor));
            }
            if(i==ry[j]){
            cell.setIcon(new javax.swing.ImageIcon("C:/Users/hp/OneDrive/Desktop/q.gif"));
            }
          //  boardCells[i] = cell;
            jPanel2.add(cell);
            
        }
      
    }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
  
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
      
    }                                           

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Hill_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Hill_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Hill_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Hill_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                new Hill_page().setVisible(true);
            }
        });
    }
    /**
     * Creates new form Hill_page
     */
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        button2 = new javax.swing.JButton();
        button1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(243, 188, 21));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));

        jTextField1.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel1.setText(" Please enter the size of the board : ");

        jPanel2.setPreferredSize(new java.awt.Dimension(261, 261));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 261, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        button2.setBackground(new java.awt.Color(0, 0, 0));
        button2.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        button2.setForeground(new java.awt.Color(243, 188, 21));
        button2.setText("Find solution");
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        button1.setBackground(new java.awt.Color(0, 0, 0));
        button1.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        button1.setForeground(new java.awt.Color(243, 188, 21));
        button1.setText("Go to main page");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\pics\\Hill Climbing algorithm.png")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N
        jLabel4.setText("Press on \"Find solution\" button, each time the N queens will be located randomly ");
        jLabel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, null, null));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(544, 544, 544)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)))
                        .addGap(0, 48, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addGap(14, 14, 14)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))))
            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1061, 501));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed
    
          jPanel2.setVisible(true);
          
         if(!jTextField1.getText().toString().equals("")){
        String n=jTextField1.getText();
        N=Integer.parseInt(n);
        if(N>8||N<4){
            jLabel3.setText("Please enter a number between 4-8!"); 
        }
         
        else{
          jPanel2.removeAll(); //if the user clicks more than one time on  the button or tries another size 
          jPanel2.revalidate(); //should be called after removing old component , to reset layout manager
         
           
            int[] s = new int[N];
            int[][] ar = new int[N][N];
            putrandom(ar, s); //pass an array and matrix , array will be filled with random values 0-N ,
            //and in the matrix one's will be put according to array
            
            displaymatrix(ar); //pass the matrix that has random queens and show it on console
            int r = find_heurstic(ar, s); //pass the matrix of random queens and the array of random values, calculate the heurestic function
            
         
            hill_algo(ar, s);         
            r = find_heurstic(ar, s);
            System.out.println("Hurestic value of this solution= " + r);
            jLabel3.setText("Hurestic value of this solution= " + r);
            printState(s);
            draw(s);
        }
         }
         else{
          jLabel3.setText("Please enter the size"); }
    }//GEN-LAST:event_button2ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
       
        main_page tp = new main_page();
         tp.setVisible(true);
        dispose();
    }//GEN-LAST:event_button1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button1;
    private javax.swing.JButton button2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
