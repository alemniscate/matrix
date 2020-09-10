package processor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
 
        Action action = new Action();
        Menu menu = new Menu();
        int menuNo = -1;
        while (menuNo != 0) {
            menuNo = menu.getMenuNo(scanner);

            switch (menuNo) {
                case 1:
                    action.add(scanner);
                    break;
                case 2:
                    action.scalar(scanner);
                    break;
                case 3:
                    action.mul(scanner);
                    break;
                case 4:
                    switch (menu.getSubMenuNo(scanner)) {
                        case 1:
                            action.transpose(scanner);
                            break;
                        case 2:
                            action.transpose_sidediagonal(scanner);
                            break;
                        case 3:
                            action.transpose_vertical(scanner);
                            break;                       
                        case 4:
                            action.transpose_horizontal(scanner);
                            break;
                    }
                    break;
                case 5:
                    action.determinant(scanner);
                    break;
                case 6:
                    action.inverse(scanner);
                    break;
                case 0:
                    break;
                default:
                    break;
            }
        }

        scanner.close();

    }
}

class Action {

    void add(Scanner scanner) {
        Matrix a = new Matrix(scanner, "first ");
        Matrix b = new Matrix(scanner, "second ");
        if (a.n != b.n || a.m != b.m) {
            System.out.println("ERROR");
            return;
        }
        a.add(b);
        a.println("add ");
    }

    void scalar(Scanner scanner) {
        Matrix a = new Matrix(scanner, "");

        System.out.println("Enter constant: ");
        String line = scanner.nextLine();
        double s = Double.parseDouble(line);
    
        a.scalar(s);
        a.println("");
    }

    void mul(Scanner scanner) {
        Matrix a = new Matrix(scanner, "first ");
        Matrix b = new Matrix(scanner, "second ");
        if (a.m != b.n) {
            System.out.println("ERROR");
            return;
        }
        Matrix c = a.mul(b);
        c.println("multiplication ");
    }

    void transpose(Scanner scanner) {
        Matrix a = new Matrix(scanner, "");
        Matrix c = a.transpose();
        c.println("");
    }

    void transpose_sidediagonal(Scanner scanner) {
        Matrix a = new Matrix(scanner, "");
        Matrix c = a.transpose_sidediagonal();
        c.println("");
    }
    
    void transpose_vertical(Scanner scanner) {
        Matrix a = new Matrix(scanner, "");
        Matrix c = a.transpose_vertical();
        c.println("");
    }
    
    void transpose_horizontal(Scanner scanner) {
        Matrix a = new Matrix(scanner, "");
        Matrix c = a.transpose_horizontal();
        c.println("");
    }

    void determinant(Scanner scanner) {
        Matrix a = new Matrix(scanner, "");
        double result = a.determinant();
        System.out.println("The result is:");
        System.out.println(result);
        System.out.println();
    }

    void inverse(Scanner scanner) {
        Matrix a = new Matrix(scanner, "");
        Matrix c = a.inverse();
        if (c.errFlag) {
            System.out.println("ERROR");
            return;
        }
        c.println("");
    }

}

class Menu {

    int getMenuNo(Scanner scanner) {
        System.out.println("1. Add matrices");
        System.out.println("2. Multiply matrix to a constant");
        System.out.println("3. Multiply matrices");
        System.out.println("4. Transpose matrix");
        System.out.println("5. Calculate a determinant");
        System.out.println("6. Inverse matrix");
        System.out.println("0. Exit");
        System.out.println("Your choice: ");

        int menuNo = -1;
        while (menuNo == -1) {
            String input = scanner.nextLine();
            if ("".equals(input)) {
                continue;
            }
            menuNo = Integer.parseInt(input);
            switch (menuNo) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 0:
                    break;
                default:
                    menuNo = -1;
                    break;    
            }
        }
        return menuNo;
    }

    int getSubMenuNo(Scanner scanner) {
        System.out.println();
        System.out.println("1. Main diagonal");
        System.out.println("2. Side diagonal");
        System.out.println("3. Vertical line");
        System.out.println("4. Horizontal line");
        System.out.println("Your choice: ");

        int menuNo = -1;
        while (menuNo == -1) {
            String input = scanner.nextLine();
            if ("".equals(input)) {
                continue;
            }
            menuNo = Integer.parseInt(input);
            switch (menuNo) {
                case 1:
                case 2:
                case 3:
                case 4:
                    break;
                default:
                    menuNo = -1;
                    break;    
            }
        }
        return menuNo;
    }

}

class Matrix {
    int n;
    int m;
    double[][] a;
    boolean errFlag = false;

    Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        a = new double[n][m];
    }

    Matrix(Scanner scanner, String kind) {
    
        System.out.println("Enter size of " + kind + "matrix: ");
        String line = scanner.nextLine();
        if ("".equals(line)) {
            line = scanner.nextLine(); 
        }
        String[] strs = line.split(" ");
        n = Integer.parseInt(strs[0]);
        m = Integer.parseInt(strs[1]);
        a = new double[n][m];
        
        System.out.println("Enter " + kind + "matrix: ");
        for (int i = 0; i < n; i++) {
            line = scanner.nextLine();
            strs = line.split(" ");
            for (int j = 0; j < m; j++) {
                a[i][j] = Double.parseDouble(strs[j]);
            }
        }

    }

    void add(Matrix b) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] += b.a[i][j];
            }
        }
    }

    void scalar(double s) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] *= s;
            }
        }
    }

    Matrix mul(Matrix b) {
        Matrix c = new Matrix(n, b.m);

        for (int i = 0; i < c.n; i++) {
            for (int j = 0; j < c.m; j++) {
                c.a[i][j] = mulElement(this, b, i, j);
            }
        }

        return c;
    }

    Matrix transpose() {
        Matrix c = new Matrix(m, n);

        for (int i = 0; i < c.n; i++) {
            for (int j = 0; j < c.m; j++) {
                c.a[i][j] = a[j][i];
            }
        }

        return c;
    }

    Matrix transpose_sidediagonal() {
        Matrix c = new Matrix(m, n);

        for (int i = 0; i < c.n; i++) {
            for (int j = 0; j < c.m; j++) {
                c.a[i][j] = a[c.m - j - 1][c.n - i - 1];
            }
        }

        return c;
    }

    Matrix transpose_vertical() {
        Matrix c = new Matrix(n, m);

        for (int i = 0; i < c.n; i++) {
            for (int j = 0; j < c.m; j++) {
                c.a[i][j] = a[i][c.m - j - 1];
            }
        }

        return c;
    }
    
    Matrix transpose_horizontal() {
        Matrix c = new Matrix(n, m);

        for (int i = 0; i < c.n; i++) {
            for (int j = 0; j < c.m; j++) {
                c.a[i][j] = a[c.n - i - 1][j];
            }
        }

        return c;
    }

    double mulElement(Matrix a, Matrix b, int row, int column) {
        double result = 0;

        for (int k = 0; k < a.m; k++) {
            result += a.a[row][k] * b.a[k][column];
        }

        return result;
    }

    double determinant() {
        if (n == 1) {
            return a[0][0];
        }

        if (n == 2) {
            return a[0][0] * a[1][1] - a[0][1] * a[1][0];
        }

        double result = 0;
        for (int j = 0; j < n; j++) {
            Matrix c = submatrix(0, j);
            double sign = j % 2 == 0 ? 1 : -1; 
            result += sign * a[0][j] * c.determinant();
        }

        return result;
    }

    Matrix submatrix(int row, int column) {
        Matrix c = new Matrix(n-1, n-1);
        int i1 = 0;
        int j1 = 0;
        for (int i = 0; i < n; i++) {
            if (i == row) {
                continue;
            }
            j1 = 0;
            for (int j = 0; j < n; j++) {
                if (j == column) {
                    continue;
                }
                c.a[i1][j1] = a[i][j];
                j1++;
            }
            i1++;
        }
        return c;
    }

    Matrix inverse() {
        double det = determinant();
        if (det == 0) {
            errFlag = true;
            return null;
        }
        Matrix c = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c.a[i][j] = cofactor(i, j);
            }
        }
        Matrix ct = c.transpose();
        ct.scalar(1 / det);
        return ct;
    }

    double cofactor(int row, int column) {
        Matrix c = submatrix(row, column);
        double sign = (row + column) % 2 == 0 ? 1 : -1; 
        return sign * c.determinant();
    }

    void println(String operation) {
        System.out.println("The " + operation + "result is: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j > 0) {
                    System.out.print(" ");
                }
                System.out.print(a[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    } 

}