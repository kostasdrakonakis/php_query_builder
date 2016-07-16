package JPanels;

import java.awt.Color;
import java.util.Random;


/**
 *
 * @author 2450-3199-3277 Ανδρέας Μανατάκης Χαράλαμπος Πολυχρονάκης Χάρης Σαριδάκης
 * @see Οι πανω μεταβλητες ειναι τα Components που χρειαζομαστε για να
 * υλοποιησουμε το παραθυρο
 * @see #getSum() () την χρηισημοποιουμε για να βγαζουμε το μεγεθος των πλοιων για να συγκρινουμε με ποσα χτυπηματα θελει ακομα ο χρηστης
 */
public class CpuBoard {

    private AircraftCarrier cpuAC;
    private Battleship cpuBA;
    private Destroyer cpuDE;
    private Submarine cpuSUB;
    private Patrolboat cpuPB;
    private Board cpuBoard;
    
    private int[] LocationsX;
    private int[] LocationsY;
    private int randomY;
    private int randomX;
    
    private boolean rotate;
    private Random randomInt;
    private Random randomBoolean;
    
    private int cpuACHit=0;
    private int cpuBAHit=0;
    private int cpuDEHit=0;
    private int cpuSUBHit=0;
    private int cpuPBHit=0;
    private int sum=0;
    

    public CpuBoard() {
        cpuAC = new AircraftCarrier();
        cpuBA = new Battleship();
        cpuDE = new Destroyer();
        cpuSUB = new Submarine();
        cpuPB = new Patrolboat();
        cpuBoard = new Board();

        LocationsX = new int[17]; // kratame tis theseis ton ploiwn ston x aksona
        LocationsY = new int[17]; // kratame tis theseis ton ploiwn ston y aksona

        randomInt = new Random();
        randomBoolean = new Random();

        randomY = randomInt.nextInt(10);
        randomX = randomInt.nextInt(10);
        rotate = randomBoolean.nextBoolean();
        
        
        if (rotate == true) {
            while (randomX + cpuAC.getShipSize() > 10) {
                randomX = randomInt.nextInt(10);
            }
            for (int i = 0; i < cpuAC.getShipSize(); i++) {
                if (cpuBoard.panels[randomX + i][randomY].getName()!= "Ship") {
//                    cpuBoard.panels[randomX + i][randomY].setBackground(Color.gray);
                    cpuBoard.panels[randomX + i][randomY].setName("Ship");
                    cpuACHit++;
                }
                LocationsX[i] = randomX + i;
                LocationsY[i] = randomY;
            }
        } else {
            while (randomY + cpuAC.getShipSize() > 10) {
                randomY = randomInt.nextInt(10);
            }
            for (int i = 0; i < cpuAC.getShipSize(); i++) {
                if (cpuBoard.panels[randomX][randomY + i].getName() != "Ship") {
//                    cpuBoard.panels[randomX][randomY + i].setBackground(Color.gray);
                    cpuBoard.panels[randomX][randomY + i].setName("Ship");
                    cpuACHit++;
                }
                LocationsX[i] = randomX;
                LocationsY[i] = randomY + i;
            }
        }

        randomY = randomInt.nextInt(10);
        randomX = randomInt.nextInt(10);

        for (int i = 0; i < LocationsX.length; i++) {
            if (randomY == LocationsY[i]) {
                randomY = randomInt.nextInt(10);
            }
            if (randomX == LocationsX[i]) {
                randomX = randomInt.nextInt(10);
            }
        }

        rotate = randomBoolean.nextBoolean();

        if (rotate == true) {
            while (randomX + cpuBA.getShipSize() > 10) {
                randomX = randomInt.nextInt(10);
            }
            for (int i = 0; i < cpuBA.getShipSize(); i++) {
                if (cpuBoard.panels[randomX + i][randomY].getName()!= "Ship") {
//                    cpuBoard.panels[randomX + i][randomY].setBackground(Color.gray);
                    cpuBoard.panels[randomX + i][randomY].setName("Ship");
                    cpuBAHit++;
                } else {
                    for (int j = i - 1; j < cpuBA.getShipSize(); j--) {
                        cpuBoard.panels[randomX + j][randomY].setBackground(Color.blue);
                        cpuBoard.panels[randomX + j][randomY].setName("BoardPanel " + i + " " + j);
                        cpuBAHit--;
                        if (j == -1) {
                            break;
                        }
                    }
                    randomX = randomInt.nextInt(10);
                    while (randomX + cpuBA.getShipSize() > 10) {
                        randomX = randomInt.nextInt(10);
                    }
                    for (int j = 0; j < cpuBA.getShipSize(); j++) {
                        if (cpuBoard.panels[randomX + j][randomY].getName() != "Ship") {
//                            cpuBoard.panels[randomX + j][randomY].setBackground(Color.gray);
                            cpuBoard.panels[randomX + j][randomY].setName("Ship");
                            cpuBAHit++;
                        }
                    }
                }
                LocationsX[i + cpuAC.getShipSize()] = randomX + i;
                LocationsY[i + cpuAC.getShipSize()] = randomY;
            }
        } else {
            while (randomY + cpuBA.getShipSize() > 10) {
                randomY = randomInt.nextInt(10);
            }
            for (int i = 0; i < cpuBA.getShipSize(); i++) {
                if (cpuBoard.panels[randomX][randomY + i].getName()!= "Ship") {
//                    cpuBoard.panels[randomX][randomY + i].setBackground(Color.gray);
                    cpuBoard.panels[randomX][randomY + i].setName("Ship");
                    cpuBAHit++;
                    
                } else {
                    for (int j = i - 1; j < cpuBA.getShipSize(); j--) {
                        cpuBoard.panels[randomX][randomY + j].setBackground(Color.blue);
                        cpuBoard.panels[randomX][randomY + j].setName("BoardPanel " + i + " " + j);
                        cpuBAHit--;
                        if (j == -1) {
                            break;
                        }
                    }
                    randomX = randomInt.nextInt(10);
                    for (int j = 0; j < cpuBA.getShipSize(); j++) {
                        if (cpuBoard.panels[randomX][randomY + j].getName()!= "Ship") {
//                            cpuBoard.panels[randomX][randomY + j].setBackground(Color.gray);
                            cpuBoard.panels[randomX][randomY + j].setName("Ship");
                            cpuBAHit++;
                        }
                    }
                }
                LocationsX[i + cpuAC.getShipSize()] = randomX;
                LocationsY[i + cpuAC.getShipSize()] = randomY + i;
            }
        }

        randomY = randomInt.nextInt(10);
        randomX = randomInt.nextInt(10);

        for (int i = 0; i < LocationsX.length; i++) {
            if (randomY == LocationsY[i]) {
                randomY = randomInt.nextInt(10);
            }
            if (randomX == LocationsX[i]) {
                randomX = randomInt.nextInt(10);
            }
        }

        rotate = randomBoolean.nextBoolean();

        if (rotate == true) {
            while (randomX + cpuDE.getShipSize() > 10) {
                randomX = randomInt.nextInt(10);
            }
            for (int i = 0; i < cpuDE.getShipSize(); i++) {
                if (cpuBoard.panels[randomX + i][randomY].getName()!= "Ship") {
//                    cpuBoard.panels[randomX + i][randomY].setBackground(Color.gray);
                    cpuBoard.panels[randomX + i][randomY].setName("Ship");
                    cpuDEHit++;
                } else {
                    for (int j = i - 1; j < cpuDE.getShipSize(); j--) {
                        cpuBoard.panels[randomX + j][randomY].setBackground(Color.blue);
                        cpuBoard.panels[randomX + j][randomY].setName("BoardPanel " + i + " " + j);
                        cpuDEHit--;
                        if (j == -1) {
                            break;
                        }
                    }
                    randomX = randomInt.nextInt(10);
                    for (int j = 0; j < cpuDE.getShipSize(); j++) {
                        if (cpuBoard.panels[randomX + j][randomY].getName()!= "Ship") {
//                            cpuBoard.panels[randomX + j][randomY].setBackground(Color.gray);
                            cpuBoard.panels[randomX + j][randomY].setName("Ship");
                            cpuDEHit++;
                        }
                    }
                }
                LocationsX[i + cpuAC.getShipSize() + cpuBA.getShipSize()] = randomX + i;
                LocationsY[i + cpuAC.getShipSize() + cpuBA.getShipSize()] = randomY;
            }
        } else {
            while (randomY + cpuDE.getShipSize() > 10) {
                randomY = randomInt.nextInt(10);
            }
            for (int i = 0; i < cpuDE.getShipSize(); i++) {
                if (cpuBoard.panels[randomX][randomY + i].getName() != "Ship") {
//                    cpuBoard.panels[randomX][randomY + i].setBackground(Color.gray);
                    cpuBoard.panels[randomX][randomY + i].setName("Ship");
                    cpuDEHit++;
                } else {
                    for (int j = i - 1; j < cpuDE.getShipSize(); j--) {
                        cpuBoard.panels[randomX][randomY + j].setBackground(Color.blue);
                        cpuBoard.panels[randomX][randomY + j].setName("BoardPanel " + i + " " + j);
                        cpuDEHit--;
                        if (j == -1) {
                            break;
                        }
                    }
                    randomX = randomInt.nextInt(10);
                    for (int j = 0; j < cpuDE.getShipSize(); j++) {
                        if (cpuBoard.panels[randomX][randomY + j].getName() != "Ship") {
//                            cpuBoard.panels[randomX][randomY + j].setBackground(Color.gray);
                            cpuBoard.panels[randomX][randomY + j].setName("Ship");
                            cpuDEHit++;
                        }
                    }
                }
                LocationsX[i + cpuAC.getShipSize() + cpuBA.getShipSize()] = randomX;
                LocationsY[i + cpuAC.getShipSize() + cpuBA.getShipSize()] = randomY + i;
            }
        }

        randomY = randomInt.nextInt(10);
        randomX = randomInt.nextInt(10);

        for (int i = 0; i < LocationsX.length; i++) {
            if (randomY == LocationsY[i]) {
                randomY = randomInt.nextInt(10);
            }
            if (randomX == LocationsX[i]) {
                randomX = randomInt.nextInt(10);
            }
        }

        rotate = randomBoolean.nextBoolean();

        if (rotate == true) {
            while (randomX + cpuSUB.getShipSize() > 10) {
                randomX = randomInt.nextInt(10);
            }
            for (int i = 0; i < cpuSUB.getShipSize(); i++) {
                if (cpuBoard.panels[randomX + i][randomY].getName()!= "Ship") {
//                    cpuBoard.panels[randomX + i][randomY].setBackground(Color.gray);
                    cpuBoard.panels[randomX + i][randomY].setName("Ship");
                    cpuSUBHit++;
                } else {
                    for (int j = i - 1; j < cpuSUB.getShipSize(); j--) {
                        cpuBoard.panels[randomX][randomY + j].setBackground(Color.blue);
                        cpuBoard.panels[randomX][randomY + j].setName("BoardPanel " + i + " " + j);
                        cpuSUBHit--;
                        if (j == -1) {
                            break;
                        }
                    }
                    randomX = randomInt.nextInt(10);
                    for (int j = 0; j < cpuDE.getShipSize(); j++) {
                        if (cpuBoard.panels[randomX + j][randomY].getName()!= "Ship") {
//                            cpuBoard.panels[randomX + j][randomY].setBackground(Color.gray);
                            cpuBoard.panels[randomX + j][randomY].setName("Ship");
                            cpuSUBHit++;
                        }
                    }
                }
                LocationsX[i + cpuAC.getShipSize() + cpuBA.getShipSize() + cpuDE.getShipSize()] = randomX + i;
                LocationsY[i + cpuAC.getShipSize() + cpuBA.getShipSize() + cpuDE.getShipSize()] = randomY;
            }
        } else {
            while (randomY + cpuSUB.getShipSize() > 10) {
                randomY = randomInt.nextInt(10);
            }
            for (int i = 0; i < cpuSUB.getShipSize(); i++) {
                if (cpuBoard.panels[randomX][randomY + i].getName()!= "Ship") {
//                    cpuBoard.panels[randomX][randomY + i].setBackground(Color.gray);
                    cpuBoard.panels[randomX][randomY + i].setName("Ship");
                    cpuSUBHit++;
                } else {
                    for (int j = i - 1; j < cpuSUB.getShipSize(); j--) {
                        cpuBoard.panels[randomX][randomY + j].setBackground(Color.blue);
                        cpuBoard.panels[randomX][randomY + j].setName("BoardPanel " + i + " " + j);
                        cpuSUBHit--;
                        if (j == -1) {
                            break;
                        }
                    }
                    randomX = randomInt.nextInt(10);
                    for (int j = 0; j < cpuSUB.getShipSize(); j++) {
                        if (cpuBoard.panels[randomX][randomY + j].getName()!= "Ship") {
//                            cpuBoard.panels[randomX][randomY + j].setBackground(Color.gray);
                            cpuBoard.panels[randomX][randomY + j].setName("Ship");
                            cpuSUBHit++;
                        }
                    }
                }

                LocationsX[i + cpuAC.getShipSize() + cpuBA.getShipSize() + cpuDE.getShipSize()] = randomX;
                LocationsY[i + cpuAC.getShipSize() + cpuBA.getShipSize() + cpuDE.getShipSize()] = randomY + i;
            }
        }

        randomY = randomInt.nextInt(10);
        randomX = randomInt.nextInt(10);

        for (int i = 0; i < LocationsX.length; i++) {
            if (randomY == LocationsY[i]) {
                randomY = randomInt.nextInt(10);
            }
            if (randomX == LocationsX[i]) {
                randomX = randomInt.nextInt(10);
            }
        }

        rotate = randomBoolean.nextBoolean();

        if (rotate == true) {
            while (randomX + cpuPB.getShipSize() > 10) {
                randomX = randomInt.nextInt(10);
            }
            for (int i = 0; i < cpuPB.getShipSize(); i++) {
                if (cpuBoard.panels[randomX + i][randomY].getName()!= "Ship") {
//                    cpuBoard.panels[randomX + i][randomY].setBackground(Color.gray);
                    cpuBoard.panels[randomX + i][randomY].setName("Ship");
                    cpuPBHit++;
                } else {
                    for (int j = i - 1; j < cpuSUB.getShipSize(); j--) {
                        cpuBoard.panels[randomX][randomY + j].setBackground(Color.blue);
                        cpuBoard.panels[randomX][randomY + j].setName("BoardPanel " + i + " " + j);
                        cpuPBHit--;
                        if (j == -1) {
                            break;
                        }
                    }
                    randomX = randomInt.nextInt(10);
                    while (randomX + cpuPB.getShipSize() > 10) {
                        randomX = randomInt.nextInt(10);
                    }
                    for (int j = 0; j < cpuPB.getShipSize(); j++) {
                        if (cpuBoard.panels[randomX + j][randomY].getName()!= "Ship") {
//                            cpuBoard.panels[randomX + j][randomY].setBackground(Color.gray);
                            cpuBoard.panels[randomX + j][randomY].setName("Ship");
                            cpuPBHit++;
                        }
                    }
                }
                LocationsX[i + cpuAC.getShipSize() + cpuBA.getShipSize() + cpuDE.getShipSize() + cpuSUB.getShipSize()] = randomX + i;
                LocationsY[i + cpuAC.getShipSize() + cpuBA.getShipSize() + cpuDE.getShipSize() + cpuSUB.getShipSize()] = randomY;
            }
        } else {
            while (randomY + cpuPB.getShipSize() > 10) {
                randomY = randomInt.nextInt(10);
            }
            for (int i = 0; i < cpuPB.getShipSize(); i++) {
                if (cpuBoard.panels[randomX][randomY + i].getName()!= "Ship") {
//                    cpuBoard.panels[randomX][randomY + i].setBackground(Color.gray);
                    cpuBoard.panels[randomX][randomY + i].setName("Ship");
                    cpuPBHit++;
                } else {
                    for (int j = i - 1; j < cpuSUB.getShipSize(); j--) {
                        cpuBoard.panels[randomX][randomY + j].setBackground(Color.blue);
                        cpuBoard.panels[randomX][randomY + j].setName("BoardPanel " + i + " " + j);
                        cpuPBHit--;
                        if (j == -1) {
                            break;
                        }
                    }
                    i--;
                    randomX = randomInt.nextInt(10);
                    for (int j = 0; j < cpuPB.getShipSize(); j++) {
                        if (cpuBoard.panels[randomX][randomY + j].getName()!= "Ship") {
//                            cpuBoard.panels[randomX][randomY + j].setBackground(Color.gray);
                            cpuBoard.panels[randomX][randomY + j].setName("Ship");
                        }
                    }
                }
                LocationsX[i + cpuAC.getShipSize() + cpuBA.getShipSize() + cpuDE.getShipSize() + cpuSUB.getShipSize()] = randomX;
                LocationsY[i + cpuAC.getShipSize() + cpuBA.getShipSize() + cpuDE.getShipSize() + cpuSUB.getShipSize()] = randomY + i;
            }
        }
        
        sum=cpuACHit+cpuBAHit+cpuDEHit+cpuSUBHit+cpuPBHit;

        
// Deixnei tis suntetagmenes gia elenxos
//        for (int i = 0; i < LocationsY.length; i++) {
//            System.out.println(LocationsX[i] + " " + LocationsY[i]);
//        }
        
    }
    public int getSum() {
        return sum;
    }
    
    public Board getCpuBoard() {
        return cpuBoard;
    }
}
