/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.edited_ai_proj;

import static com.mycompany.edited_ai_proj.Hill_page.N;
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
public class sim_page extends javax.swing.JFrame {

  
    
   private int[] queens;
   private JLabel[] boardCells;
    /**
     * Creates new form Page3
     */
    public sim_page() {
        initComponents();
          setTitle("Simulated Annealing");
           jPanel2.setVisible(false);
    }
      public static void putrandom(int[][] ar, int[] s) {
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            s[i] = rand.nextInt(N);
            ar[s[i]][i] = 1;
        }
    }
    public static void printArray(int[][] ar) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(ar[i][j] + " ");
            }
            System.out.println();
        }
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
    public static int calc_h(int[][] ar, int[] s) {
        int attacking = 0;
        int row, col;

        for (int i = 0; i < N; i++) {
            if (ar[s[i]][i] == 1) {
                row = s[i];
                col = i - 1;
                while (col >= 0) {
                    if (ar[row][col] == 1) {
                        attacking++;
                    }
                    col--;
                }

                row = s[i];
                col = i + 1;
                while (col < N) {
                    if (ar[row][col] == 1) {
                        attacking++;
                    }
                    col++;
                }

                row = s[i] - 1;
                col = i - 1;
                while (col >= 0 && row >= 0) {
                    if (ar[row][col] == 1) {
                        attacking++;
                    }
                    col--;
                    row--;
                }

                row = s[i] + 1;
                col = i + 1;
                while (col < N && row < N) {
                    if (ar[row][col] == 1) {
                        attacking++;
                    }
                    col++;
                    row++;
                }

                row = s[i] + 1;
                col = i - 1;
                while (col >= 0 && row < N) {
                    if (ar[row][col] == 1) {
                        attacking++;
                    }
                    col--;
                    row++;
                }

                row = s[i] - 1;
                col = i + 1;
                while (col < N && row >= 0) {
                    if (ar[row][col] == 1) {
                        attacking++;
                    }
                    col++;
                    row--;
                }
            }
        }

        return attacking / 2;
    }
    public static void getopChild(int[][] ar, int[] s) {
        int[][] opar = new int[N][N];
        int[] ops = new int[N];
        int e;

        copyState(ops, s);
        generateBoard(opar, ops);

        int h_current = calc_h(opar, ops);

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

                    int h_next = calc_h(childar, childs);

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
    queens = new int[N];
    boardCells = new JLabel[N * N];
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
            System.out.println("Hurestic value of this solution= " + i +j);
            cell.setIcon(new javax.swing.ImageIcon("C:/Users/hp/OneDrive/Desktop/q.gif"));
            }
           
            boardCells[i] = cell;
            jPanel2.add(cell);
            
        }
      
    }
    }

     private  int[][] simulatedAnnealing(double t,double co,double it ,int[] bests ) {
        int iteration=0;
        
        int[][] currentState=new int[N][N];
        int[] s = new int[N];
        int[][] nextState = new int[N][N];
        int[] nexts = new int[N];

        int[][] bestState=new int[N][N];
        
        putrandom(currentState,s);
        
        copyState(bests,s);
        generateBoard(bestState,bests);
        
        int currenth = calc_h(currentState,s);
        printArray(currentState);
        System.out.print(currenth+"\n");

        while (currenth > 0 && t > it) {
            t = t * co;
            
            nextState = getrandomchild(currentState,s,nexts);
            int nexth = calc_h(nextState,nexts);
            
             System.out.print("hue "+ nexth+"\n");
            
            currenth = calc_h(currentState,s);
        
            
            int e = currenth-nexth;
              if(e>0){
                copyState(s,nexts);
                generateBoard(currentState,s);
              
                currenth = calc_h(currentState,s);
               
                
                int besth = calc_h(bestState,bests);
                currenth = calc_h(currentState,s);
                
                if(besth>currenth){
                    copyState(bests,s);
                    generateBoard(bestState,bests);
                  
                }
            }else if ( Math.exp(-e / t) > Math.round( Math.random() )) {
                copyState(s,nexts);
                generateBoard(currentState,s);
            }


            iteration++;
          
        }

        System.out.println("Number of iterations= " + iteration);
        jLabel7.setText("Number of iterations= " + iteration);
         return bestState;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(243, 188, 21));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));

        jLabel1.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel1.setText(" Size of board");

        jLabel2.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jLabel2.setText("Please enter the four values below respectively, size of the board, initial temperature,");

        jLabel3.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel3.setText(" Initial Tempreture");

        jLabel4.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel4.setText("Cooling rate");

        jLabel5.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        jLabel5.setText("Maximum iteration");

        jTextField1.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N

        jTextField2.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N

        jTextField3.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N

        jTextField4.setFont(new java.awt.Font("Georgia", 1, 12)); // NOI18N

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(243, 188, 21));
        jButton1.setText("Find solution");
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(243, 188, 21));
        jButton2.setText("Go to main page");
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jLabel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jLabel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setPreferredSize(new java.awt.Dimension(261, 261));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 261, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 261, Short.MAX_VALUE)
        );

        jLabel9.setFont(new java.awt.Font("Georgia", 1, 14)); // NOI18N
        jLabel9.setText(" cooling rate and maximum iteration. ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(19, 19, 19)
                                        .addComponent(jLabel3))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel1)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(21, 21, 21))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(18, 243, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jPanel2.setVisible(true);
         // TODO add your handling code here:
         if(!(jTextField1.getText().toString().equals("")||jTextField2.getText().toString().equals("") || jTextField3.getText().toString().equals("") || jTextField4.getText().toString().equals(""))){
        String n=jTextField1.getText();
        N=Integer.parseInt(n);
        if(N>8||N<4){
            jLabel7.setText("Please enter a number between 4-8"); 
        }
        else{
            jPanel2.removeAll();
            jPanel2.revalidate();
            int iteration=0;
           
            double t=Double.parseDouble(jTextField2.getText());
            double co=Double.parseDouble(jTextField4.getText());
            double it=Double.parseDouble(jTextField3.getText());
            int[] bests = new int[N];
            int[][] solution = simulatedAnnealing(t,co,it,bests);
            System.out.println("next\n");
            printArray(solution);
            int r = calc_h(solution, bests);
            jLabel6.setText("Hurestic value of this solution= " + r);
            draw(bests);
        }  
         }
          else{
            jLabel6.setText("Please fill all the texts" );
            jLabel7.setText("   " );
        }
    }                                       

   
    public static void main(String args[]) {
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new sim_page().setVisible(true);
            }
        });
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    main_page tp = new main_page();
         tp.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed
 private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           
                                     

  
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
