package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner sc = new Scanner(System.in);

        char[][] grid = new char[3][3];
        populateGrid(grid, "_________");
        printGrid(grid);

        char player = 'X';
        boolean isGameOn = true;
        while (isGameOn) {
            boolean inputNotTaken = true;
            while (inputNotTaken) {
                try {
                    String gridInput = sc.nextLine();
                    int[] gridInputs = Arrays.stream(gridInput.split(" "))
                            .mapToInt(Integer::parseInt)
                            .toArray();
                    int row = gridInputs[0];
                    int col = gridInputs[1];

                    if (!(row >= 1 && row <= 3) || !(col >= 1 && col <= 3)) {
                        System.out.println("Coordinates should be from 1 to 3");
                    } else if (grid[row - 1][col - 1] != '_') {
                        System.out.println("This cell is occupied! Choose another one!");
                    } else {
                        grid[row - 1][col - 1] = player;
                        printGrid(grid);
                        player = player == 'X' ? 'O' : 'X';
                        inputNotTaken = false;
                        String gameStatus = analyzeGameState(grid);
                        printGameStatus(gameStatus);
                        if (gameStatus == "Impossible" || gameStatus == "Draw" || gameStatus == "X wins" || gameStatus == "O wins") {
                            isGameOn = false;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("You should enter numbers!");
                }
            }
        }
    }

    static void populateGrid(char[][] grid, String input) {
        StringBuilder str = new StringBuilder(input);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = str.charAt(0);
                str.delete(0, 1);
            }
        }
    }

    static void printGrid(char[][] grid) {
        String output = "---------\n";
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (j == 0) {
                    output += "| " + grid[i][j] + " ";
                } else if (j == 2) {
                    output += grid[i][j] + " " + "|\n";
                } else {
                    output += grid[i][j] + " ";
                }
            }
        }
        output += "---------";
        System.out.println(output);
    }

    static void printGameStatus(String gameStatus) {
        switch (gameStatus) {
            case "Impossible":
                System.out.println("Impossible");
                break;
            case "Game not finished":
                break;
            case "Draw":
                System.out.println("Draw");
                break;
            case "X wins":
                System.out.println("X wins");
                break;
            case "O wins":
                System.out.println("O wins");
                break;
        }
    }

    static String analyzeGameState(char[][] grid) {
//        Impossible state check
        if ((checkWin(grid, 'X') && checkWin(grid, 'O')) ||
                (getMarkerCount(grid, 'X') - getMarkerCount(grid, 'O') > 1) ||
                (getMarkerCount(grid, 'O') - getMarkerCount(grid, 'X') > 1)
        ) {
            return "Impossible";

        }

//        Game ongoing check
        if (!checkWin(grid, 'X') && !checkWin(grid, 'O') && getMarkerCount(grid, '_') > 0) {
            return "Game not finished";
        }

//        Draw check
        if (!checkWin(grid, 'X') && !checkWin(grid, 'O') && getMarkerCount(grid, '_') == 0) {
            return "Draw";
        }

//        X won check
        if (checkWin(grid, 'X')) {
            return "X wins";
        }

//        O won check
//        if (checkWin(grid, 'O')) {
        return "O wins";
//        }
    }

    static int getMarkerCount(char[][] grid, char marker) {
        int markerCount = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == marker) {
                    markerCount++;
                }
            }
        }
        return markerCount;
    }

    static boolean checkWin(char[][] grid, char marker) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (checkRowWin(grid, marker, i, j) || checkColumnWin(grid, marker, i, j) || checkDiagonalWin(grid, marker, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean checkRowWin(char[][] grid, char marker, int i, int j) {
        if (j == 0) {
            return grid[i][j] == marker &&
                    grid[i][j + 1] == marker &&
                    grid[i][j + 2] == marker;
        } else if (j == 1) {
            return grid[i][j] == marker &&
                    grid[i][j - 1] == marker &&
                    grid[i][j + 1] == marker;
        } else if (j == 2) {
            return grid[i][j] == marker &&
                    grid[i][j - 1] == marker &&
                    grid[i][j - 2] == marker;
        } else {
            return false;
        }
    }

    static boolean checkColumnWin(char[][] grid, char marker, int i, int j) {
        if (i == 0) {
            return grid[i][j] == marker &&
                    grid[i + 1][j] == marker &&
                    grid[i + 2][j] == marker;
        } else if (i == 1) {
            return grid[i][j] == marker &&
                    grid[i - 1][j] == marker &&
                    grid[i + 1][j] == marker;
        } else if (i == 2) {
            return grid[i][j] == marker &&
                    grid[i - 1][j] == marker &&
                    grid[i - 2][j] == marker;
        } else {
            return false;
        }
    }

    static boolean checkDiagonalWin(char[][] grid, char marker, int i, int j) {
        if (i == 0) {
            if (j == 0) {
                return grid[i][j] == marker &&
                        grid[i + 1][j + 1] == marker &&
                        grid[i + 2][j + 2] == marker;
            } else if (j == 2) {
                return grid[i][j] == marker &&
                        grid[i + 1][j - 1] == marker &&
                        grid[i + 2][j - 2] == marker;
            } else {
                return false;
            }
        } else if (i == 1) {
            if (j == 1) {
                return (grid[i][j] == marker &&
                        grid[i - 1][j - 1] == marker &&
                        grid[i + 1][j + 1] == marker) ||
                        (grid[i][j] == marker &&
                                grid[i - 1][j + 1] == marker &&
                                grid[i + 1][j - 1] == marker);
            } else {
                return false;
            }
        } else if (i == 2) {
            if (j == 0) {
                return grid[i][j] == marker &&
                        grid[i - 1][j + 1] == marker &&
                        grid[i - 2][j + 2] == marker;
            } else if (j == 2) {
                return grid[i][j] == marker &&
                        grid[i - 1][j - 1] == marker &&
                        grid[i - 2][j - 2] == marker;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
