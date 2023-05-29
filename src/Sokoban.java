import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;
import java.util.function.*;
import tester.*;
import java.util.ArrayList;

//  represents a Content cell in a board
interface IContentCell {
  // Accepts a function object
  <U> U accept(IContentCellVisitor<U> visitor);

  // Determines if this content cell is a trophy of same color as given target
  // color
  boolean sameColorTargetBeneath(String targetColor);

  // determines if this content cell can be moved onto
  boolean canBeMovedOnto();

  // determines if this content cell destroys content
  boolean destroysContent();

  // determines if this content cell can be pushed
  boolean canBePushed();

  // determines if this content cell is immovable
  boolean isImmovable();
}

// Class representing a wall
class Wall implements IContentCell {
  // Accepts a function object
  public <U> U accept(IContentCellVisitor<U> visitor) {
    return visitor.visitWall(this);
  }

  // Determines if this content cell is a trophy of same color as given target
  // color
  public boolean sameColorTargetBeneath(String targetColor) {
    return false;
  }

  // determines if this wall cell can be moved onto
  public boolean canBeMovedOnto() {
    return false;
  }

  // determines if this wall cell destroys content
  public boolean destroysContent() {
    return false;
  }

  // determines if this wall cell can be moved onto
  public boolean canBePushed() {
    return false;
  }

  // determines if this wall is immovable
  public boolean isImmovable() {
    return true;
  }
}

// Class representing a box
class Box implements IContentCell {
  // Accepts a function object
  public <U> U accept(IContentCellVisitor<U> visitor) {
    return visitor.visitBox(this);
  }

  // Determines if this content cell is a trophy of same color as given target
  // color
  public boolean sameColorTargetBeneath(String targetColor) {
    return false;
  }

  // determines if this box can be moved onto
  public boolean canBeMovedOnto() {
    return false;
  }

  // determine if this box destroys content
  public boolean destroysContent() {
    return false;
  }

  // determines if this box can be pushed
  public boolean canBePushed() {
    return true;
  }

  // determines if this box is immovable
  public boolean isImmovable() {
    return false;
  }
}

//Player Template Class representing a player
class Player implements IContentCell {
  String direction; // <, >, ^, v

  // Constructor for player
  Player(String direction) {
    this.direction = new Utils().validatePlayerDirection(direction);
  }

  // Accepts a function object
  public <U> U accept(IContentCellVisitor<U> visitor) {
    return visitor.visitPlayer(this);
  }

  // Determines if this content cell is a trophy of same color as given target
  // color
  public boolean sameColorTargetBeneath(String targetColor) {
    return false;
  }

  // determines if this player can be moved onto
  public boolean canBeMovedOnto() {
    return false;
  }

  // determines if this player destroys content
  public boolean destroysContent() {
    return false;
  }

  // determines if this player can be pushed
  public boolean canBePushed() {
    return false;
  }

  // determines if this player is immovable
  public boolean isImmovable() {
    return false;
  }
}

// Class representing a trophy
class Trophy implements IContentCell {
  String color; // r, b, g, y

  // Constructor for Trophy
  Trophy(String color) {
    this.color = new Utils().validateTrophyColor(color);
  }

  // Accepts a function object
  public <U> U accept(IContentCellVisitor<U> visitor) {
    return visitor.visitTrophy(this);
  }

  // Determines if this content cell is a trophy of same color as given target
  // color
  public boolean sameColorTargetBeneath(String targetColor) {
    return this.color.equals(targetColor.toLowerCase());
  }

  // determines if this trophy can be moved onto
  public boolean canBeMovedOnto() {
    return false;
  }

  // determines if this trophy destroys content
  public boolean destroysContent() {
    return false;
  }

  // determines if this trophy can be pushed
  public boolean canBePushed() {
    return true;
  }

  // determines if this trophy is immovable
  public boolean isImmovable() {
    return false;
  }
}

// Class representing no content
class BlankContent implements IContentCell {
  // Accepts a function object
  public <U> U accept(IContentCellVisitor<U> visitor) {
    return visitor.visitBlankContent(this);
  }

  // Determines if this content cell is a trophy of same color as given target
  // color
  public boolean sameColorTargetBeneath(String targetColor) {
    return false;
  }

  // determines if this blank content and be moved onto
  public boolean canBeMovedOnto() {
    return true;
  }

  // determines if this blank content destroys content
  public boolean destroysContent() {
    return false;
  }

  // determines if this blank content can be pushed
  public boolean canBePushed() {
    return false;
  }

  // determines if this blank content is immovable
  public boolean isImmovable() {
    return false;
  }
}

// Class representing a hole
class Hole implements IContentCell {
  // Accepts a function object
  public <U> U accept(IContentCellVisitor<U> visitor) {
    return visitor.visitHole(this);
  }

  // Determines if this content cell is a trophy of same color as given target
  // color
  public boolean sameColorTargetBeneath(String targetColor) {
    return false;
  }

  // determines if this hole can be moved onto
  public boolean canBeMovedOnto() {
    return false;
  }

  // determines if this hole destroys content
  public boolean destroysContent() {
    return true;
  }

  // determines if this hole can be pushed
  public boolean canBePushed() {
    return false;
  }

  // determines if this hole is immovable
  public boolean isImmovable() {
    return true;
  }
}

// interface representing a type of ground for a cell One
interface IGroundCell {

  // Accepts a function object
  <U> U accept(IGroundCellVisitor<U> visitor);

  // Determines if this cell is complete
  boolean completedCell(IContentCell content);

  // Determines if this content is slidable
  boolean canBeSlidOn();

}

// Class representing a target
class Target implements IGroundCell {
  String color; // R, G, B, Y

  // Constructor for Target
  Target(String color) {
    this.color = new Utils().validateTargetColor(color);
  }

  // Accepts a function object
  public <U> U accept(IGroundCellVisitor<U> visitor) {
    return visitor.visitTarget(this);
  }

  // Determines if this cell is complete
  public boolean completedCell(IContentCell content) {
    return content.sameColorTargetBeneath(this.color);
  }

  // determines if this target can be slid on
  public boolean canBeSlidOn() {
    return false;
  }
}

// Class representing nothing on the ground
class BlankGround implements IGroundCell {

  // Accepts a function object
  public <U> U accept(IGroundCellVisitor<U> visitor) {
    return visitor.visitBlankGround(this);
  }

  // Determines if this cell is complete
  public boolean completedCell(IContentCell content) {
    return true;
  }

  // determines if this blank ground can be slid on
  public boolean canBeSlidOn() {
    return false;
  }
}

//Class representing a patch of ice
class Ice implements IGroundCell {
  // Accepts a function object
  public <U> U accept(IGroundCellVisitor<U> visitor) {
    return visitor.visitIce(this);
  }

  // Determines if this cell is complete
  public boolean completedCell(IContentCell content) {
    return true;
  }

  // determines if this ice canbeslid on
  public boolean canBeSlidOn() {
    return true;
  }
}

// Class representing a single cell in a Sokoban level
class Cell {
  IContentCell content; // Stuff on top - wall, trophy, player, blank
  IGroundCell ground; // Stuff on bottom - target, blank

  // Constructor for Cell
  Cell(IContentCell content, IGroundCell ground) {
    this.content = content;
    this.ground = ground;
  }

  // Determines if this cell is complete for purposes of winning a level (trophy
  // on target)
  boolean isComplete() {
    return this.ground.completedCell(this.content);
  }

  // Determines if the content of this cell is blank
  boolean hasContentThatCanBeMovedOnto() {
    return this.content.canBeMovedOnto();
  }

  // Determines if the content of this cell can be pushed
  boolean hasContentThatCanBePushed() {
    return this.content.canBePushed();
  }

  // Determines if the content of this cell is a hole
  boolean hasContentThatDestroys() {
    return this.content.destroysContent();
  }

  // determines if this cell can be slid on
  boolean canBeSlidOn() {
    return this.ground.canBeSlidOn();
  }

  // determines if this cell is immovable
  boolean isImmovable() {
    return this.content.isImmovable();
  }

}

// Class representing a Sokoban level
class Board {
  ArrayList<ArrayList<Cell>> cells; // 2D list representing the level's cells.
  // To be more specific: cells is a list of rows (top -> bottom).
  // A row is a list of cells (left -> right).

  Posn playerLoc;

  // Convenience constructor from strings
  Board(String contents, String ground) {
    this.cells = new Utils().initializeCells(contents, ground);
    this.playerLoc = this.getLocation(new RowContainsPlayer(), new DoesThisCellHaveAPlayer());
  }

  // Default constructor from list of list of cells
  Board(ArrayList<ArrayList<Cell>> cells) {
    this.cells = cells;
    this.playerLoc = this.getLocation(new RowContainsPlayer(), new DoesThisCellHaveAPlayer());
  }

  // Returns a single image representing the board
  WorldImage render() {
    return new Utils().drawCells(this.cells);
  }

  // Determines if this level is won (trophies on correct targets)
  boolean levelWon() {
    return new ArrayUtils().andMap(this.cells, new CompletedRow());
  }

  // Returns the same board with the player moved up if possible
  Board movePlayerUp() {
    return this.movePlayer(this.playerLoc, new Posn(this.playerLoc.x - 1, this.playerLoc.y),
        new Player("^"));
  }

  // Returns the same board with the player moved down if possible
  Board movePlayerDown() {
    return this.movePlayer(this.playerLoc, new Posn(this.playerLoc.x + 1, this.playerLoc.y),
        new Player("v"));
  }

  // Returns the same board with the player moved left if possible
  Board movePlayerRight() {
    return this.movePlayer(this.playerLoc, new Posn(this.playerLoc.x, this.playerLoc.y + 1),
        new Player(">"));
  }

  // Returns the same board with the player moved right if possible
  Board movePlayerLeft() {
    return this.movePlayer(this.playerLoc, new Posn(this.playerLoc.x, this.playerLoc.y - 1),
        new Player("<"));
  }

  // Returns whether the board contains a player
  boolean hasAPlayer() {
    return this.playerLoc.x != -1;
  }

  // Gets the first (left -> right then top -> bottom) index of a cell
  Posn getLocation(Function<ArrayList<Cell>, Boolean> rowFunc,
      Function<Cell, Boolean> elementFunc) {
    int row = new ArrayUtils().getIndexIf(cells, rowFunc);
    int column;
    if (row == -1) {
      column = -1;
    }
    else {
      column = new ArrayUtils().getIndexIf(cells.get(row), elementFunc);
    }

    return new Posn(row, column);
  }

  // Moves the player from the old location to the new location (if possible).
  // newPlayer is just the player oriented facing the correct direction.
  Board movePlayer(Posn oldPlayerLoc, Posn newPlayerLoc, IContentCell newPlayer) {
    // This won't ever happen since walls surround the board, but useful check
    if (newPlayerLoc.x >= this.cells.size() || newPlayerLoc.y >= this.cells.get(0).size()
        || newPlayerLoc.x < 0 || newPlayerLoc.y < 0) {
      return this;
    }

    ArrayList<ArrayList<Cell>> recentCells = this.cells;
    ArrayList<ArrayList<Cell>> newCells = new ArrayList<ArrayList<Cell>>();

    // Performs a deep copy of the board so that undesired mutation is avoided
    for (ArrayList<Cell> row : recentCells) {
      ArrayList<Cell> newRow = new ArrayList<Cell>();
      for (Cell cell : row) {
        newRow.add(new Cell(cell.content, cell.ground));
      }
      newCells.add(newRow);
    }

    boolean hitAHole = false;

    Board newBoard = new Board(newCells);

    Cell oldPlayerCell = newBoard.cells.get(oldPlayerLoc.x).get(oldPlayerLoc.y);
    Cell newPlayerCell = newBoard.cells.get(newPlayerLoc.x).get(newPlayerLoc.y);

    Posn otherSidePosition = new Posn(newPlayerLoc.x + (newPlayerLoc.x - oldPlayerLoc.x),
        newPlayerLoc.y + (newPlayerLoc.y - oldPlayerLoc.y));

    IContentCell contentAdjToPlayer = newPlayerCell.content;

    // If the player can move onto the adjacent cell
    if (contentAdjToPlayer.canBeMovedOnto()) {

      newPlayerCell.content = newPlayer;
      oldPlayerCell.content = new BlankContent();
    }

    // If the player can push the content of the adjacent cell
    else if (contentAdjToPlayer.canBePushed()) {
      int rowChange = newPlayerLoc.x - oldPlayerLoc.x;
      int colChange = newPlayerLoc.y - oldPlayerLoc.y;
      Cell otherSide = newBoard.cells.get(newPlayerLoc.x + rowChange)
          .get(newPlayerLoc.y + colChange);
      // Moves box into blank spot and player into the vacated trophy spot.
      if (otherSide.hasContentThatCanBeMovedOnto()) {
        otherSide.content = newPlayerCell.content;
        newPlayerCell.content = newPlayer;
        oldPlayerCell.content = new BlankContent();

        if (otherSide.canBeSlidOn()) {
          int currRow = otherSidePosition.x;
          int currCol = otherSidePosition.y;
          Cell currCell = newBoard.cells.get(currRow).get(currCol);
          Cell nextCell = newBoard.cells.get(currRow + rowChange).get(currCol + colChange);

          // If we push a box or trophy onto ice, we want it to travel until it
          // either collides with something, moves off the ice, or hits a hole
          // We shift the item until one of these things happens
          while (currCell.canBeSlidOn()
              && (nextCell.hasContentThatCanBeMovedOnto() || nextCell.hasContentThatDestroys())
              && !hitAHole) {
            IContentCell newNextCellContent = currCell.content;
            IContentCell newCurrCellContent = new BlankContent();
            if (nextCell.hasContentThatDestroys()) {
              newNextCellContent = new BlankContent();
              hitAHole = true;
            }
            nextCell.content = newNextCellContent;
            currCell.content = newCurrCellContent;

            currRow = currRow + rowChange;
            currCol = currCol + colChange;
            currCell = nextCell;
            nextCell = newBoard.cells.get(currRow + rowChange).get(currCol + colChange);
          }

        }
      }
      // Moves box into hole (meaning they disappear) and moves player into vacated
      // trophy spot.
      else if (otherSide.hasContentThatDestroys()) {
        otherSide.content = new BlankContent();
        newPlayerCell.content = newPlayer;
        oldPlayerCell.content = new BlankContent();
      }

    }
    // Deals with moving a player onto a hole
    else if (contentAdjToPlayer.destroysContent()) {

      newPlayerCell.content = new BlankContent();
      oldPlayerCell.content = new BlankContent();
      hitAHole = true;
    }
    // Recursively repeats if our player is on ice and isn't next to an immovable
    // object
    if (newPlayerCell.canBeSlidOn() && !newPlayerCell.isImmovable()
        && !newPlayerCell.hasContentThatCanBePushed() && !hitAHole) {
      return newBoard.movePlayer(newPlayerLoc, otherSidePosition, newPlayer);
    }

    // If the player isn't on ice, that means we're in a stable board position
    // Hence, we return the updated board.
    return new Board(newBoard.cells);
  }

}

// Utils class for ArrayList
class ArrayUtils {
  // Foldr for ArrayList
  <T, U> U foldr(ArrayList<T> arr, BiFunction<T, U, U> func, U base) {
    // Loops through the list from right to left.
    // Applies the function to each element and the base.
    // Updates the base and continues.
    for (int i = arr.size() - 1; i >= 0; i -= 1) {
      base = func.apply(arr.get(i), base);
    }
    return base;
  }

  // Andmap for ArrayList
  <T> boolean andMap(ArrayList<T> arr, Function<T, Boolean> func) {
    // Loops through each element in the list and
    // checks if the function returns true when applied to this element.
    // If not, returns false. If every element passes, returns true.
    for (T t : arr) {
      if (!func.apply(t)) {
        return false;
      }
    }
    return true;
  }

  // gets the index of first element that satisfies the function, else return -1
  <T> int getIndexIf(ArrayList<T> arr, Function<T, Boolean> func) {
    // Loops through each element and if the element passes the function, returns
    // index.
    // Returns -1 if none of the elements pass.
    for (int i = 0; i < arr.size(); i += 1) {
      if (func.apply(arr.get(i))) {
        return i;
      }
    }
    return -1;
  }

}

// Utils Class to help constructors
class Utils {
  // Helper to return a list of list of cells from given strings
  ArrayList<ArrayList<Cell>> initializeCells(String contents, String grounds) {
    if (contents.length() != grounds.length()) {
      throw new IllegalArgumentException("Content and Ground must be same number of cells");
    }
    else {
      ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();
      ArrayList<Cell> row = new ArrayList<Cell>();
      // Loops through the contents, adding cells to each row.
      // When we stumble across a "\n", we add the row to the 2d list (board)
      // We then repeat with an empty row until we reach the end of the String.
      for (int i = 0; i < contents.length(); i += 1) {
        if (contents.substring(i, i + 1).equals("\n") && grounds.substring(i, i + 1).equals("\n")) {
          cells.add(row);
          row = new ArrayList<Cell>();
        }
        else if (contents.substring(i, i + 1).equals("\n")
            || grounds.substring(i, i + 1).equals("\n")) {
          throw new IllegalArgumentException("Content and Ground must be same number of cells");
        }
        else {
          String currentContent = contents.substring(i, i + 1);
          String currentGround = grounds.substring(i, i + 1);

          IContentCell content;
          IGroundCell ground;

          if (currentContent.equals("W")) {
            content = new Wall();
          }
          else if (currentContent.equals("B")) {
            content = new Box();
          }
          else if (currentContent.equals(">") || currentContent.equals("<")
              || currentContent.equals("v") || currentContent.equals("^")) {
            content = new Player(currentContent);
          }
          else if (currentContent.equals("y") || currentContent.equals("g")
              || currentContent.equals("b") || currentContent.equals("r")) {
            content = new Trophy(currentContent);
          }
          else if (currentContent.equals("H")) {
            content = new Hole();
          }
          else {
            content = new BlankContent();
          }
          if (currentGround.equals("Y") || currentGround.equals("G") || currentGround.equals("B")
              || currentGround.equals("R")) {
            ground = new Target(currentGround);
          }
          else if (currentGround.equals("I")) {
            ground = new Ice();
          }
          else {
            ground = new BlankGround();
          }
          row.add(new Cell(content, ground));
        }
      }
      cells.add(row);
      return (cells);
    }
  }

  // Draws the cells in a board
  WorldImage drawCells(ArrayList<ArrayList<Cell>> cells) {
    return new ArrayUtils().foldr(cells, new DrawColumn(),
        new RectangleImage(0, 0, OutlineMode.SOLID, Color.WHITE));
  }

  // Helper to validate trophy color for constructor
  String validateTrophyColor(String color) {
    if (color.equals("r") || color.equals("y") || color.equals("g") || color.equals("b")) {
      return color;
    }
    else {
      throw new IllegalArgumentException("Invalid trophy color");
    }
  }

  // Helper to validate target color for constructor
  String validateTargetColor(String color) {
    if (color.equals("R") || color.equals("Y") || color.equals("G") || color.equals("B")) {
      return color;
    }
    else {
      throw new IllegalArgumentException("Invalid target color");
    }
  }

  // Helper to validate player direction for constructor
  String validatePlayerDirection(String direction) {
    if (direction.equals("^") || direction.equals("v") || direction.equals(">")
        || direction.equals("<")) {
      return direction;
    }
    else {
      throw new IllegalArgumentException("Invalid player direction");
    }
  }
}

// Visitor interface to implement a function object over ICells
interface IContentCellVisitor<R> extends Function<IContentCell, R> {
  // Visits wall class
  R visitWall(Wall wall);

  // Visits box class
  R visitBox(Box box);

  // Visits player class
  R visitPlayer(Player player);

  // Visits trophy class
  R visitTrophy(Trophy trophy);

  // Visits blank content class
  R visitBlankContent(BlankContent bc);

  // Visits hole class
  R visitHole(Hole hole);
}

// Visitor Function Class that returns the image for each type of content
class DrawContent implements IContentCellVisitor<WorldImage> {

  // Draws a piece of content
  public WorldImage apply(IContentCell content) {
    return content.accept(this);
  }

  // Draws a wall
  public WorldImage visitWall(Wall wall) {
    return new FromFileImage("./images/Wall.png");
  }

  // Draws a box
  public WorldImage visitBox(Box box) {
    return new FromFileImage("./images/Box.png");
  }

  // Draws a player
  public WorldImage visitPlayer(Player player) {
    return new FromFileImage("./images/Player" + player.direction + ".png");
  }

  // Draws a trophy
  public WorldImage visitTrophy(Trophy trophy) {
    return new FromFileImage("./images/Trophy" + trophy.color + ".png");
  }

  // Draws blank content
  public WorldImage visitBlankContent(BlankContent bc) {
    return new RectangleImage(0, 0, "solid", Color.WHITE);
  }

  // Draws a hole.
  public WorldImage visitHole(Hole hole) {
    return new FromFileImage("./images/Hole.png");
  }

}

// Visitor Function Class that returns if a content is a player

class IsAPlayer implements IContentCellVisitor<Boolean> {
  // Applies the object to the piece of content
  public Boolean apply(IContentCell content) {
    return content.accept(this);
  }

  // A wall is not a player
  public Boolean visitWall(Wall wall) {
    return false;
  }

  // A box is not a player
  public Boolean visitBox(Box box) {
    return false;
  }

  // A player is a player
  public Boolean visitPlayer(Player player) {
    return true;
  }

  // A trophy is not a player
  public Boolean visitTrophy(Trophy trophy) {
    return false;
  }

  // A blank is not a player
  public Boolean visitBlankContent(BlankContent bc) {
    return false;
  }

  // A hole is not a player
  public Boolean visitHole(Hole hole) {
    return false;
  }
}

// Visitor interface to implement a function over
interface IGroundCellVisitor<R> extends Function<IGroundCell, R> {
  // Visits target class
  R visitTarget(Target target);

  // Visits blank ground class
  R visitBlankGround(BlankGround bg);

  // Visits ice class
  R visitIce(Ice ice);
}

// Visitor Function Class that returns the image for each
class DrawGround implements IGroundCellVisitor<WorldImage> {

  // Draws the ground
  public WorldImage apply(IGroundCell ground) {
    return ground.accept(this);
  }

  // Draws a target
  public WorldImage visitTarget(Target target) {
    Color color;
    if (target.color.equals("R")) {
      color = Color.RED;
    }
    else if (target.color.equals("G")) {
      color = Color.GREEN;
    }
    else if (target.color.equals("B")) {
      color = Color.BLUE;
    }
    else {
      color = Color.YELLOW;
    }
    return new CircleImage(30, "solid", color);
  }

  // Draws a blank ground.
  public WorldImage visitBlankGround(BlankGround bg) {
    return new RectangleImage(60, 60, "solid", Color.WHITE);
  }

  // Draws a patch of ice
  public WorldImage visitIce(Ice ice) {
    return new FromFileImage("./images/Ice.png");
  }
}

// Function Class that returns the image for a cell
class DrawCell implements Function<Cell, WorldImage> {
  // Applies this function to the cell
  public WorldImage apply(Cell cell) {
    return new OverlayImage(new DrawContent().apply(cell.content),
        new DrawGround().apply(cell.ground));
  }
}

//Draws a Row of Cells(List of Cells) by placing each cell next to each other
class DrawRow implements BiFunction<Cell, WorldImage, WorldImage> {
  // Applies this function to the cell and current image so far
  public WorldImage apply(Cell t, WorldImage u) {
    return new BesideImage(new DrawCell().apply(t), u);
  }
}

//Draws a Column of Cell Lists(List of List of Cells) by 
// placing each list of cells beneath each other
class DrawColumn implements BiFunction<ArrayList<Cell>, WorldImage, WorldImage> {
  public WorldImage apply(ArrayList<Cell> t, WorldImage u) {
    return new AboveImage(new ArrayUtils().foldr(t, new DrawRow(),
        new RectangleImage(0, 0, OutlineMode.SOLID, Color.WHITE)), u);
  }
}

/*
 * CompletedRow Template Function Class that determines if a row of cells is
 * "complete" (trophies on targets) Needed b/c we need to andMap this andMap in
 * the checkComplete process
 * 
 * Methods: WorldImage apply(IList<Cell> row)
 */
class CompletedRow implements Function<ArrayList<Cell>, Boolean> {
  // Applies this function to the list of cells
  public Boolean apply(ArrayList<Cell> row) {
    /* Everything in IList<T> template */
    return new ArrayUtils().andMap(row, new CompletedCell());
  }
}

/*
 * CompletedCell Template Function Class that determines if a cell is "complete"
 * (trophies on targets)
 * 
 * Methods: WorldImage apply(Cell cell)
 */
class CompletedCell implements Function<Cell, Boolean> {
  // Applies this function to the cell
  public Boolean apply(Cell cell) {
    /* Everything in cell template */
    return cell.isComplete();
  }
}

/*
 * RowContainsPlayer Template Function Class that determines if there is a
 * player cell in this row
 * 
 * Methods: WorldImage apply(IList<Cell> row)
 */
class RowContainsPlayer implements Function<ArrayList<Cell>, Boolean> {
  // Applies this function to the list of cells
  public Boolean apply(ArrayList<Cell> row) {
    /* Everything in IList<T> template */
    return new ArrayUtils().getIndexIf(row, new DoesThisCellHaveAPlayer()) != -1;
  }
}

// Represents a Function object to Determine if a cell has a player
class DoesThisCellHaveAPlayer implements Function<Cell, Boolean> {
  // Applies this function to the cell
  public Boolean apply(Cell cell) {
    /* Everything in cell template */
    return new IsAPlayer().apply(cell.content);
  }
}

/*
 * PlayLevel Template Class that represents a World for a Sokoban level
 * 
 * Fields: Board board
 * 
 * Methods: WorldScene makeScene() World onTick() World onKeyEvent(String key)
 * 
 * Methods on Fields: board.getImages() board.render() board.levelWon()
 * board.movePlayerUp() board.movePlayerDown() board.movePlayerRight()
 * board.movePlayerLeft()
 */
class PlayLevel extends World {

  ArrayList<Board> moveList; // List of previous board states
  int moves; // Number of moves
  Board board; // Board being rendered in the world

  // Convenience Constructor
  PlayLevel(Board board) {
    this.board = board;
    this.moveList = new ArrayList<Board>();
    this.moves = 0;
  }

  // Convenience Constructor
  PlayLevel(Board board, ArrayList<Board> moveList, int moves) {
    this.board = board;
    this.moveList = moveList;
    this.moves = moves;
  }

  // Makes the initial scene
  public WorldScene makeScene() {
    return new WorldScene(800, 800).placeImageXY(this.board.render(), 400, 400).placeImageXY(
        new TextImage("Moves: " + Integer.toString(this.moves), 80, FontStyle.BOLD, Color.BLACK),
        400, 60);
  }

  // Remakes world every tick
  public World onTick() {
    if (this.board.levelWon()) {
      return new FinishedLevel(this.board, "You Won!", Color.GREEN, this.moves);
    }
    else if (!this.board.hasAPlayer()) {
      return new FinishedLevel(this.board, "You Lost!", Color.RED, this.moves);
    }
    else {
      return new PlayLevel(this.board, this.moveList, this.moves);
    }
  }

  // Handles key press events
  public World onKeyEvent(String key) {
    /* See String class */

    Board newBoard = this.board;
    if (key.equals("up")) {
      newBoard = this.board.movePlayerUp();
    }
    else if (key.equals("down")) {
      newBoard = this.board.movePlayerDown();
    }
    else if (key.equals("right")) {
      newBoard = this.board.movePlayerRight();
    }
    else if (key.equals("left")) {
      newBoard = this.board.movePlayerLeft();
    }
    else if (key.equals("u")) {
      return this.undo();
    }

    Posn newPlayerLoc = newBoard.playerLoc;
    Posn oldPlayerLoc = this.board.playerLoc;
    if (newPlayerLoc.x != oldPlayerLoc.x || newPlayerLoc.y != oldPlayerLoc.y) {
      this.moveList.add(this.board);
      this.moves += 1;
    }
    return new PlayLevel(newBoard, this.moveList, this.moves);
  }

  // Performs the undo function by reverting the world into its previous board
  // state
  public World undo() {
    if (this.moveList.size() > 0) {
      int lastIndex = this.moveList.size() - 1;
      Board recentBoard = this.moveList.get(lastIndex);
      ArrayList<ArrayList<Cell>> recentCells = recentBoard.cells;
      ArrayList<ArrayList<Cell>> newCells = new ArrayList<ArrayList<Cell>>();

      // Performs a deep copy to avoid undesired mutation of boards
      for (ArrayList<Cell> row : recentCells) {
        ArrayList<Cell> newRow = new ArrayList<Cell>();
        for (Cell cell : row) {
          newRow.add(cell);
        }
        newCells.add(newRow);
      }

      Board newBoard = new Board(newCells);
      this.moveList.remove(lastIndex);
      this.moves += 1;
      return new PlayLevel(newBoard, this.moveList, this.moves);
    }
    else {
      return new PlayLevel(this.board, this.moveList, this.moves);
    }
  }
}

// Represents a World for a finished level (win or lose)
class FinishedLevel extends World {
  Board board;
  String message;
  Color color;
  int moves;

  // Convenience constructor
  FinishedLevel(Board board, String message, Color color, int moves) {
    this.board = board;
    this.message = message;
    this.color = color;
    this.moves = moves;
  }

  // Renders world, which contains message on top of board.
  public WorldScene makeScene() {
    return new WorldScene(800, 800).placeImageXY(this.board.render(), 400, 400)
        .placeImageXY(new TextImage(this.message, 80, FontStyle.BOLD, this.color), 400, 400)
        .placeImageXY(new TextImage("Moves: " + Integer.toString(this.moves), 80, FontStyle.BOLD,
            Color.BLACK), 400, 60);
  }

}

//Examples Class
class ExamplesSokobans {
  // Testing library output was used to verify constructor correctness,
  // as well as correct visual output
  String exampleLevelGround = "________\n" + "___R____\n" + "________\n" + "_B____Y_\n"
      + "________\n" + "___G____\n" + "________";

  String exampleLevelContents = "__WWW___\n" + "__W_WW__\n" + "WWWr_WWW\n" + "W_b>yB_W\n"
      + "WW_gWWWW\n" + "_WW_W___\n" + "__WWW___";

  String completedLevelContents = "__WWW___\n" + "__WrWW__\n" + "WWW__WWW\n" + "Wb_>_ByW\n"
      + "WW__WWWW\n" + "_WWgW___\n" + "__WWW___";
  String almostCompletedLevelContents = "__WWW___\n" + "__WrWW__\n" + "WWW__WWW\n" + "Wb__yB>W\n"
      + "WW__WWWW\n" + "_WWgW___\n" + "__WWW___";

  String introLevelContents = "__WWWWW_\n" + "WWW___W_\n" + "W_>b__W_\n" + "WWW_g_W_\n"
      + "W_WWy_W_\n" + "W_W___WW\n" + "Wr_bgr_W\n" + "W______W\n" + "WWWWWWWW";

  String introLevelContentsWithABox = "__WWWWW_\n" + "WWW_B_W_\n" + "W_>b__W_\n" + "WWW_g_W_\n"
      + "W_WWy_W_\n" + "W_W___WW\n" + "Wr_bgr_W\n" + "W______W\n" + "WWWWWWWW";

  String introLevelGround = "________\n" + "________\n" + "_B______\n" + "_____G__\n" + "_R______\n"
      + "____Y___\n" + "___B__R_\n" + "____G___\n" + "________";

  String levelWithHoleContents = "WWWWWWW\n" + "W_>___W\n" + "W_H_r_W\n" + "WH_HB_W\n" + "W_H___W\n"
      + "W_____W\n" + "WWWWWWW";

  String levelWithHoleGround = "_______\n" + "_______\n" + "_______\n" + "__R____\n" + "_______\n"
      + "_______\n" + "_______";

  String exampleIceContents = "_WWWWWWW\n" + "WW_____W\n" + "WH___y<W\n" + "WW____WW\n"
      + "_WWWWWW_";

  String exampleIceGround = "________\n" + "__YI____\n" + "__III___\n" + "________\n" + "________";

  Cell cell1 = new Cell(new Wall(), new BlankGround());
  Cell cell2 = new Cell(new BlankContent(), new Target("R"));
  Cell cell3 = new Cell(new Player(">"), new Target("Y"));
  Cell cell4 = new Cell(new Box(), new BlankGround());

  ArrayList<ArrayList<Cell>> twoByTwoL = new ArrayList<ArrayList<Cell>>();

  Board example = new Board(exampleLevelContents, exampleLevelGround);

  // Board board1 = new Board(twoByTwo);

  Board board2 = new Board("WW_W\nW>BW\nW_bW\nWB_W", "__R_\n_Y__\n__G_\n_BB_");

  Board board3 = new Board(">WB_>_B", "YYYYYYY");

  Board completedBoard = new Board(completedLevelContents, exampleLevelGround);

  Board almostCompletedBoard = new Board(almostCompletedLevelContents, exampleLevelGround);

  Board introLevelBoard = new Board(introLevelContents, introLevelGround);
  Board introLevelBoardWithABox = new Board(introLevelContentsWithABox, introLevelGround);

  Board levelWithHole = new Board(levelWithHoleContents, levelWithHoleGround);

  Board levelWithIce = new Board(exampleIceContents, exampleIceGround);

  // test for IContentCell accept
  boolean testContentAccept(Tester t) {
    return t.checkExpect(new Wall().accept(new IsAPlayer()), false)
        && t.checkExpect(new Box().accept(new IsAPlayer()), false)
        && t.checkExpect(new Player(">").accept(new IsAPlayer()), true);
  }

  // test for sameColorBeneath
  boolean testSameColorBeneath(Tester t) {
    return t.checkExpect(new Wall().sameColorTargetBeneath("r"), false)
        && t.checkExpect(new Trophy("r").sameColorTargetBeneath("r"), true)
        && t.checkExpect(new Trophy("r").sameColorTargetBeneath("b"), false);
  }

  // test for GroundCell accept method
  boolean testGroundCellAccept(Tester t) {
    return t.checkExpect(new Target("R").accept(new DrawGround()),
        new CircleImage(30, "solid", Color.RED));
  }

  // test for completedCell
  boolean testCompletedCell(Tester t) {
    return t.checkExpect(new Target("R").completedCell(new Trophy("r")), true)
        && t.checkExpect(new Target("R").completedCell(new Trophy("b")), false)
        && t.checkExpect(new Target("R").completedCell(new Player(">")), false)
        && t.checkExpect(new BlankGround().completedCell(new Trophy("b")), true);
  }

  // test for isComplete
  boolean testCellIsComplete(Tester t) {
    return t.checkExpect(new Cell(new Trophy("r"), new Target("R")).isComplete(), true)
        && t.checkExpect(new Cell(new BlankContent(), new BlankGround()).isComplete(), true)
        && t.checkExpect(new Cell(new Trophy("b"), new Target("R")).isComplete(), false);
  }

  // test for hasContentThatCanBeMovedOnto
  boolean testHasContentThatCanBeMovedOnto(Tester t) {
    return t.checkExpect(new Cell(new Trophy("r"), new Target("R")).hasContentThatCanBeMovedOnto(),
        false)
        && t.checkExpect(
            new Cell(new BlankContent(), new BlankGround()).hasContentThatCanBeMovedOnto(), true)
        && t.checkExpect(
            new Cell(new BlankContent(), new Target("R")).hasContentThatCanBeMovedOnto(), true);
  }

  // test for moveup, moveleft, moveright, movedown, and moveplayer
  boolean testMove(Tester t) {
    String exampleSmallGround = "____\n" + "____\n" + "____\n" + "____";
    String exampleSmallContents = "WWWW\n" + "W_>W\n" + "W__W\n" + "WWWW";

    Board smallBoard = new Board(exampleSmallContents, exampleSmallGround);

    String exampleLevelGround = "________\n" + "___R____\n" + "________\n" + "_B____Y_\n"
        + "________\n" + "___G____\n" + "________";
    String exampleLevelContents = "__WWW___\n" + "__W_WW__\n" + "WWWr_WWW\n" + "W_b>_B_W\n"
        + "WW_gWWWW\n" + "_WW_W___\n" + "__WWW___";

    Board largeBoard = new Board(exampleLevelContents, exampleLevelGround);

    String exampleLevelContentsBox = "__WWW___\n" + "__W_WW__\n" + "WWWr_WWW\n" + "W_b_>B_W\n"
        + "WW_gWWWW\n" + "_WW_W___\n" + "__WWW___";

    Board largeBoardBox = new Board(exampleLevelContentsBox, exampleLevelGround);

    String exampleSmallContentsDown = "WWWW\n" + "W__W\n" + "W_vW\n" + "WWWW";

    Board smallBoardDown = new Board(exampleSmallContentsDown, exampleSmallGround);

    String exampleContentHole = "WWWWW\n" + "W___W\n" + "W>H_W\n" + "WWWWW";

    String exampleGroundHole = "_____\n" + "_____\n" + "_____\n" + "_____";

    Board boardWithHole = new Board(exampleContentHole, exampleGroundHole);

    String exampleContentHoleFell = "WWWWW\n" + "W___W\n" + "W___W\n" + "WWWWW";
    Board boardPlayerFallIntoHole = new Board(exampleContentHoleFell, exampleGroundHole);

    String exampleContentBoxAndHole = "WWWWW\n" + "W___W\n" + "W>BHW\n" + "WWWWW";

    Board boardWithBoxAndHole = new Board(exampleContentBoxAndHole, exampleGroundHole);

    String exampleContentBoxInHole = "WWWWW\n" + "W___W\n" + "W_>_W\n" + "WWWWW";

    Board boxFellInHole = new Board(exampleContentBoxInHole, exampleGroundHole);

    String exampleContentIce = "WWWWWW\n" + "W____W\n" + "W>___W\n" + "WWWWWW";
    String exampleGroundIce = "______\n" + "______\n" + "__II__\n" + "______";

    Board boardWithIce = new Board(exampleContentIce, exampleGroundIce);

    String exampleContentAfterIce = "WWWWWW\n" + "W____W\n" + "W___>W\n" + "WWWWWW";

    Board boardAfterSlidingIce = new Board(exampleContentAfterIce, exampleGroundIce);

    String exampleContentTrophyBeforeIce = "WWWWWW\n" + "W____W\n" + "W>r__W\n" + "WWWWWW";

    String exampleGroundIceOne = "______\n" + "______\n" + "___I__\n" + "______";

    Board boardBeforeIce = new Board(exampleContentTrophyBeforeIce, exampleGroundIceOne);

    String exampleContentTrophyAfterIce = "WWWWWW\n" + "W____W\n" + "W_>_rW\n" + "WWWWWW";

    Board boardAfterIce = new Board(exampleContentTrophyAfterIce, exampleGroundIceOne);

    String exampleContentTrophySpawnOnIce = "WWWWWW\n" + "W____W\n" + "W>r__W\n" + "WWWWWW";

    Board boardTrophyOnIce = new Board(exampleContentTrophySpawnOnIce, exampleGroundIce);

    String exampleContentTrophySpawnOnIceAfter = "WWWWWW\n" + "W____W\n" + "W__>rW\n" + "WWWWWW";

    Board boardTrophyHitWall = new Board(exampleContentTrophySpawnOnIceAfter, exampleGroundIce);

    String exampleIceAndHoleContents = "_WWWWWWW\n" + "WW_____W\n" + "W_HH_y<W\n" + "WW___WWW\n"
        + "_WWWWW__";

    String exampleIceAndHoleGround = "________\n" + "__YI____\n" + "__III___\n" + "________\n"
        + "________";

    Board trophySlideIntoHole = new Board(exampleIceAndHoleContents, exampleIceAndHoleGround);

    String exampleIceAndHoleContentsAfter = "_WWWWWWW\n" + "WW_____W\n" + "W_H__<_W\n"
        + "WW___WWW\n" + "_WWWWW__";

    String examplePlayerIceAndHoleContents = "_WWWWWWW\n" + "WW_____W\n" + "W_HH_<_W\n"
        + "WW___WWW\n" + "_WWWWW__";

    String examplePlayerIceAndHoleGround = "________\n" + "__YI____\n" + "__III___\n" + "________\n"
        + "________";

    Board playerIceAndHole = new Board(examplePlayerIceAndHoleContents,
        examplePlayerIceAndHoleGround);

    String examplePlayerIceAndHoleContentsAfter = "_WWWWWWW\n" + "WW_____W\n" + "W_H____W\n"
        + "WW___WWW\n" + "_WWWWW__";

    Board playerIceAndHoleAfter = new Board(examplePlayerIceAndHoleContentsAfter,
        examplePlayerIceAndHoleGround);
    Board trophySlideIntoHoleAfter = new Board(exampleIceAndHoleContentsAfter,
        exampleIceAndHoleGround);
    return t.checkExpect(smallBoard.movePlayerUp(), smallBoard)
        && t.checkExpect(smallBoard.movePlayerRight(), smallBoard)
        && t.checkExpect(smallBoard.movePlayerDown(), smallBoardDown)
        && t.checkExpect(boardWithHole.movePlayerRight(), boardPlayerFallIntoHole)
        && t.checkExpect(boardWithBoxAndHole.movePlayerRight(), boxFellInHole)
        && t.checkExpect(boardWithIce.movePlayerRight(), boardAfterSlidingIce)
        && t.checkExpect(boardTrophyOnIce.movePlayerRight(), boardTrophyHitWall)
        && t.checkExpect(boardBeforeIce.movePlayerRight(), boardAfterIce)
        && t.checkExpect(trophySlideIntoHole.movePlayerLeft(), trophySlideIntoHoleAfter)
        && t.checkExpect(playerIceAndHole.movePlayerLeft(), playerIceAndHoleAfter);
  }

  // test for hasAPlayer method
  boolean testHasAPlayer(Tester t) {
    String exampleLevelGround = "________\n" + "___R____\n" + "________\n" + "_B>___Y_\n"
        + "________\n" + "___G____\n" + "________";
    String exampleLevelContents = "__WWW___\n" + "__W_WW__\n" + "WWWr_WWW\n" + "W_b>_B_W\n"
        + "WW_gWWWW\n" + "_WW_W___\n" + "__WWW___";

    String exampleLevelContentsNoPlayer = "__WWW___\n" + "__W_WW__\n" + "WWWr_WWW\n" + "W_b__B_W\n"
        + "WW_gWWWW\n" + "_WW_W___\n" + "__WWW___";

    Board boardPlayer = new Board(exampleLevelContents, exampleLevelGround);
    Board boardNoPlayer = new Board(exampleLevelContentsNoPlayer, exampleLevelGround);
    return t.checkExpect(boardPlayer.hasAPlayer(), true)
        && t.checkExpect(boardNoPlayer.hasAPlayer(), false);
  }

  // test for andmap
  boolean testAndMap(Tester t) {
    return t.checkExpect(new ArrayUtils().andMap(example.cells, new CompletedRow()), false)
        && t.checkExpect(new ArrayUtils().andMap(board2.cells, new CompletedRow()), false)
        && t.checkExpect(new ArrayUtils().andMap(completedBoard.cells, new CompletedRow()), true)
        && t.checkExpect(new ArrayUtils().andMap(almostCompletedBoard.cells, new CompletedRow()),
            false);
  }

  // Test for levelWon
  boolean testLevelWon(Tester t) {
    return t.checkExpect(example.levelWon(), false) && t.checkExpect(board2.levelWon(), false)
        && t.checkExpect(completedBoard.levelWon(), true)
        && t.checkExpect(almostCompletedBoard.levelWon(), false);
  }

  // Test for getLocation
  boolean testGetLocation(Tester t) {
    return t.checkExpect(board2.getLocation(new RowContainsPlayer(), new DoesThisCellHaveAPlayer()),
        new Posn(1, 1))
        && t.checkExpect(
            example.getLocation(new RowContainsPlayer(), new DoesThisCellHaveAPlayer()),
            new Posn(3, 3))
        && t.checkExpect(almostCompletedBoard.getLocation(new RowContainsPlayer(),
            new DoesThisCellHaveAPlayer()), new Posn(3, 6));
  }

  // test for getIndexIf in array utils
  boolean testGetIndexIf(Tester t) {
    String exampleLevelGround = "________\n" + "___R____\n" + "________\n" + "_B>___Y_\n"
        + "________\n" + "___G____\n" + "________";

    String exampleLevelContentsNoPlayer = "__WWW___\n" + "__W_WW__\n" + "WWWr_WWW\n" + "W_b__B_W\n"
        + "WW_gWWWW\n" + "_WW_W___\n" + "__WWW___";

    Board boardNoPlayer = new Board(exampleLevelContentsNoPlayer, exampleLevelGround);
    return t.checkExpect(new ArrayUtils().getIndexIf(example.cells, new RowContainsPlayer()), 3)
        && t.checkExpect(new ArrayUtils().getIndexIf(boardNoPlayer.cells, new RowContainsPlayer()),
            -1);
  }

  // test for initialize cells. (Exception testing at the end)
  boolean testInitializeCells(Tester t) {
    String ground = "____\n" + "____\n" + "____\n" + "____";

    String cell = "W_b>\n" + "W_b>\n" + "W_b>\n" + "W_b>";
    ArrayList<Cell> rowGround = new ArrayList<Cell>();
    rowGround.add(new Cell(new Wall(), new BlankGround()));
    rowGround.add(new Cell(new BlankContent(), new BlankGround()));
    rowGround.add(new Cell(new Trophy("b"), new BlankGround()));
    rowGround.add(new Cell(new Player(">"), new BlankGround()));

    ArrayList<ArrayList<Cell>> finalBoard = new ArrayList<ArrayList<Cell>>();
    finalBoard.add(rowGround);
    finalBoard.add(rowGround);
    finalBoard.add(rowGround);
    finalBoard.add(rowGround);

    return t.checkExpect(new Utils().initializeCells(cell, ground), finalBoard);
  }

  // test for IsAPlayer function object
  boolean testIsAPlayer(Tester t) {
    return t.checkExpect(new IsAPlayer().apply(new Hole()), false)
        && t.checkExpect(new IsAPlayer().apply(new Player(">")), true)
        && t.checkExpect(new IsAPlayer().apply(new Wall()), false)
        && t.checkExpect(new IsAPlayer().visitWall(new Wall()), false)
        && t.checkExpect(new IsAPlayer().visitBox(new Box()), false)
        && t.checkExpect(new IsAPlayer().visitPlayer(new Player(">")), true)
        && t.checkExpect(new IsAPlayer().visitTrophy(new Trophy("b")), false)
        && t.checkExpect(new IsAPlayer().visitBlankContent(new BlankContent()), false)
        && t.checkExpect(new IsAPlayer().visitHole(new Hole()), false);
  }

  // test for CompletedRow apply method function object
  boolean testCompletedRowApply(Tester t) {
    String exampleLevelGround = "________\n" + "___R____\n" + "________\n" + "_B>___Y_\n"
        + "________\n" + "___G____\n" + "________";
    String exampleLevelContents = "__WWW___\n" + "__W_WW__\n" + "WWWr_WWW\n" + "Wrb>_B_W\n"
        + "WW_gWWWW\n" + "_WW_W___\n" + "__WWW___";

    Board havePlayer = new Board(exampleLevelContents, exampleLevelGround);
    return t.checkExpect(new CompletedRow().apply(havePlayer.cells.get(2)), true)
        && t.checkExpect(new CompletedRow().apply(havePlayer.cells.get(3)), false);
  }

  // test for completedCell class apply
  boolean testCompletedCellClass(Tester t) {
    return t.checkExpect(new CompletedCell().apply(new Cell(new Player(">"), new Target("B"))),
        false)
        && t.checkExpect(new CompletedCell().apply(new Cell(new Trophy("b"), new Target("B"))),
            true)
        && t.checkExpect(new CompletedCell().apply(new Cell(new Trophy("r"), new Target("B"))),
            false);
  }

  // test for RowContainsPlayer function object
  boolean testRowContainsPlayer(Tester t) {
    String exampleLevelGround = "________\n" + "___R____\n" + "________\n" + "_B>___Y_\n"
        + "________\n" + "___G____\n" + "________";
    String exampleLevelContents = "__WWW___\n" + "__W_WW__\n" + "WWWr_WWW\n" + "W_b>_B_W\n"
        + "WW_gWWWW\n" + "_WW_W___\n" + "__WWW___";

    Board havePlayer = new Board(exampleLevelContents, exampleLevelGround);
    return t.checkExpect(new RowContainsPlayer().apply(havePlayer.cells.get(3)), true)
        && t.checkExpect(new RowContainsPlayer().apply(havePlayer.cells.get(2)), false);
  }

  // test for DoesThisCellHaveAPlayer function object
  boolean testDoesThisCellHaveAPlayer(Tester t) {
    return t.checkExpect(
        new DoesThisCellHaveAPlayer().apply(new Cell(new Player(">"), new BlankGround())), true)
        && t.checkExpect(
            new DoesThisCellHaveAPlayer().apply(new Cell(new Trophy("b"), new Target("B"))), false);
  }

  // test for validate trophy color in Utils class
  boolean testValidateTrophyColor(Tester t) {
    Utils util = new Utils();
    return t.checkExpect(new Utils().validateTrophyColor("r"), "r")
        // invalid trophy color test
        && t.checkException(new IllegalArgumentException("Invalid trophy color"), util,
            "validateTrophyColor", "R");
  }

  // test for validate target color in Utils class
  boolean testValidateTargetColor(Tester t) {
    Utils util = new Utils();
    return t.checkExpect(new Utils().validateTargetColor("R"), "R")
        // invalid target color test
        && t.checkException(new IllegalArgumentException("Invalid target color"), util,
            "validateTargetColor", "r");
  }

  // test for validate player direction in Utils class
  boolean testValidatePlayerDirection(Tester t) {
    Utils util = new Utils();
    return t.checkExpect(new Utils().validatePlayerDirection(">"), ">")
        // invalid player direction test
        && t.checkException(new IllegalArgumentException("Invalid player direction"), util,
            "validatePlayerDirection", "a");
  }

  // test for IGroundCellVisitor's visitor methods
  boolean testGroundCellVisitor(Tester t) {
    return t.checkExpect(new DrawGround().visitTarget(new Target("R")),
        new CircleImage(30, "solid", Color.RED))
        && t.checkExpect(new DrawGround().visitBlankGround(new BlankGround()),
            new RectangleImage(60, 60, "solid", Color.WHITE))
        && t.checkExpect(new DrawGround().visitIce(new Ice()),
            new FromFileImage("./images/Ice.png"));
  }

  // test for DrawGround function object
  boolean testDrawGroundApply(Tester t) {
    return t.checkExpect(new DrawGround().apply(new Target("R")),
        new CircleImage(30, "solid", Color.RED));
  }

  // test for DrawContent function object
  boolean testDrawContent(Tester t) {
    return t.checkExpect(new DrawContent().apply(new Box()), new FromFileImage("./images/Box.png"));
  }

  // test for DrawCell function object
  boolean testDrawCell(Tester t) {
    return t.checkExpect(new DrawCell().apply(new Cell(new BlankContent(), new BlankGround())),
        new OverlayImage(new RectangleImage(0, 0, OutlineMode.SOLID, Color.WHITE),
            new RectangleImage(60, 60, OutlineMode.SOLID, Color.WHITE)));
  }

  // test for DrawRow function object
  boolean testDrawRow(Tester t) {
    return t.checkExpect(
        new DrawRow().apply(new Cell(new BlankContent(), new BlankGround()),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.BLUE)),
        new BesideImage(new DrawCell().apply(new Cell(new BlankContent(), new BlankGround())),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.BLUE)));
  }

  // test for drawColumn function object
  boolean testDrawColumn(Tester t) {
    ArrayList<Cell> drawThis = new ArrayList<Cell>();
    drawThis.add(new Cell(new BlankContent(), new BlankGround()));
    return t.checkExpect(
        new DrawColumn().apply(drawThis,
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.WHITE)),
        new AboveImage(
            new ArrayUtils().foldr(drawThis, new DrawRow(),
                new RectangleImage(0, 0, OutlineMode.SOLID, Color.WHITE)),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.WHITE)));
  }

  // test for foldr
  boolean testFoldr(Tester t) {
    ArrayList<Cell> listOfCells = new ArrayList<Cell>();
    listOfCells.add(new Cell(new BlankContent(), new BlankGround()));
    return t.checkExpect(
        new ArrayUtils().foldr(listOfCells, new DrawRow(),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.BLUE)),
        new BesideImage(new DrawCell().apply(new Cell(new BlankContent(), new BlankGround())),
            new RectangleImage(25, 25, OutlineMode.SOLID, Color.BLUE)));
  }

  // test for drawcells
  boolean testDrawCells(Tester t) {
    ArrayList<Cell> listOfCells = new ArrayList<Cell>();
    listOfCells.add(new Cell(new BlankContent(), new BlankGround()));
    ArrayList<ArrayList<Cell>> boardOfCells = new ArrayList<ArrayList<Cell>>();
    boardOfCells.add(listOfCells);
    return t.checkExpect(new Utils().drawCells(boardOfCells),
        new AboveImage(
            new BesideImage(new DrawCell().apply(new Cell(new BlankContent(), new BlankGround())),
                new RectangleImage(0, 0, OutlineMode.SOLID, Color.WHITE)),
            new RectangleImage(0, 0, OutlineMode.SOLID, Color.WHITE)));
  }

  // test for render
  boolean testRender(Tester t) {
    ArrayList<Cell> listOfCells = new ArrayList<Cell>();
    listOfCells.add(new Cell(new BlankContent(), new BlankGround()));
    ArrayList<ArrayList<Cell>> cellsListBlank = new ArrayList<ArrayList<Cell>>();
    cellsListBlank.add(listOfCells);
    Board boardWithCells = new Board(cellsListBlank);
    return t.checkExpect(boardWithCells.render(),
        new AboveImage(
            new BesideImage(new DrawCell().apply(new Cell(new BlankContent(), new BlankGround())),
                new RectangleImage(0, 0, OutlineMode.SOLID, Color.WHITE)),
            new RectangleImage(0, 0, OutlineMode.SOLID, Color.WHITE)));
  }

  // Exceptions when drawing a board with uneven cell/ground contents
  boolean testExceptionInitializeCell(Tester t) {
    String exampleLevelGroundBad = "________\n" + "___R____\n" + "________\n" + "_B____Y_\n"
        + "________\n" + "___G____\n" + "________";
    String exampleLevelContentsBad = "__WWW___\n" + "__W_WW__\n" + "WWWr_WWW\n" + "W_b>B__W\n"
        + "WW_gWWWW\n" + "_WW_W___\n";
    Utils util = new Utils();
    return t.checkException(
        new IllegalArgumentException("Content and Ground must be same number of cells"), util,
        "initializeCells", "exampleLevelGroundBad", "exampleLevelContentsBad");
  }

  // test for makeScene in PlayLevel
  boolean testMakeScene(Tester t) {
    return t.checkExpect(new PlayLevel(example).makeScene(),
        new WorldScene(800, 800).placeImageXY(example.render(), 400, 400).placeImageXY(
            new TextImage("Moves: " + Integer.toString(0), 80, FontStyle.BOLD, Color.BLACK), 400,
            60));
  }

  // test for onTick in PlayLevel
  boolean testOnTick(Tester t) {
    String exampleLevelGroundNoPlayer = "________\n" + "___R____\n" + "________\n" + "_B____Y_\n"
        + "________\n" + "___G____\n" + "________";
    String exampleLevelContentsNoPlayer = "__WWW___\n" + "__W_WW__\n" + "WWWr_WWW\n" + "W_b_B__W\n"
        + "WW_gWWWW\n" + "_WW_W___\n" + "__WWW___";
    Board noPlayerBoard = new Board(exampleLevelContentsNoPlayer, exampleLevelGroundNoPlayer);
    return t.checkExpect(new PlayLevel(example).onTick(), new PlayLevel(example))
        && t.checkExpect(new PlayLevel(completedBoard).onTick(),
            new FinishedLevel(completedBoard, "You Won!", Color.GREEN, 0))
        && t.checkExpect(new PlayLevel(noPlayerBoard).onTick(),
            new FinishedLevel(noPlayerBoard, "You Lost!", Color.RED, 0));
  }

  // test for onkeyevent in PlayLevel class
  boolean testOnKeyEvent(Tester t) {
    String exampleSmallGround = "____\n" + "____\n" + "____\n" + "____";
    String exampleSmallContents = "WWWW\n" + "W_>W\n" + "W__W\n" + "WWWW";

    Board smallBoard = new Board(exampleSmallContents, exampleSmallGround);

    String exampleSmallContentsLeft = "WWWW\n" + "W<_W\n" + "W__W\n" + "WWWW";

    String exampleSmallContentsDown = "WWWW\n" + "W__W\n" + "W_vW\n" + "WWWW";

    Board smallBoardLeft = new Board(exampleSmallContentsLeft, exampleSmallGround);
    Board smallBoardDown = new Board(exampleSmallContentsDown, exampleSmallGround);

    ArrayList<Board> moveList = new ArrayList<Board>();
    moveList.add(smallBoard);
    return t.checkExpect(new PlayLevel(smallBoard).onKeyEvent("up"), new PlayLevel(smallBoard))
        && t.checkExpect(new PlayLevel(smallBoard).onKeyEvent("right"), new PlayLevel(smallBoard))
        && t.checkExpect(new PlayLevel(smallBoard).onKeyEvent("down"),
            new PlayLevel(smallBoardDown, moveList, 1))
        && t.checkExpect(new PlayLevel(smallBoard).onKeyEvent("left"),
            new PlayLevel(smallBoardLeft, moveList, 1));
  }

  // test for class FinishedLevel method's makeScene and onTick
  boolean testFinishedLevel(Tester t) {
    return t.checkExpect(new FinishedLevel(example, "You won", Color.RED, 5).makeScene(),
        new WorldScene(800, 800).placeImageXY(example.render(), 400, 400)
            .placeImageXY(new TextImage("You won", 80, FontStyle.BOLD, Color.RED), 400, 400)
            .placeImageXY(
                new TextImage("Moves: " + Integer.toString(5), 80, FontStyle.BOLD, Color.BLACK),
                400, 60));
  }

  // test for canbeslidon in ground class
  boolean testCanBeSlidOn(Tester t) {
    return t.checkExpect(new BlankGround().canBeSlidOn(), false)
        && t.checkExpect(new Ice().canBeSlidOn(), true)
        && t.checkExpect(new Target("R").canBeSlidOn(), false);
  }

  // canBeSlidOn in Cell class
  boolean testHasContentThatCanBeSlidOn(Tester t) {
    return t.checkExpect(new Cell(new BlankContent(), new BlankGround()).canBeSlidOn(), false)
        && t.checkExpect(new Cell(new Wall(), new Ice()).canBeSlidOn(), true)
        && t.checkExpect(new Cell(new Box(), new Target("R")).canBeSlidOn(), false);
  }

  // is Immovable in IContentCell
  boolean testIsImmovable(Tester t) {
    return t.checkExpect(new BlankContent().isImmovable(), false)
        && t.checkExpect(new Wall().isImmovable(), true)
        && t.checkExpect(new Box().isImmovable(), false)
        && t.checkExpect(new Trophy("r").isImmovable(), false)
        && t.checkExpect(new Player(">").isImmovable(), false)
        && t.checkExpect(new Hole().isImmovable(), true);
  }

  // is canBeMoved in IContentCell
  boolean testCanBeMoved(Tester t) {
    return t.checkExpect(new BlankContent().canBeMovedOnto(), true)
        && t.checkExpect(new Wall().canBeMovedOnto(), false)
        && t.checkExpect(new Box().canBeMovedOnto(), false)
        && t.checkExpect(new Trophy("r").canBeMovedOnto(), false)
        && t.checkExpect(new Player(">").canBeMovedOnto(), false)
        && t.checkExpect(new Hole().canBeMovedOnto(), false);
  }

  // isImmovable in Cell class
  boolean testHasContentImmovableMoved(Tester t) {
    return t.checkExpect(new Cell(new BlankContent(), new BlankGround()).isImmovable(), false)
        && t.checkExpect(new Cell(new Wall(), new BlankGround()).isImmovable(), true)
        && t.checkExpect(new Cell(new Box(), new BlankGround()).isImmovable(), false)
        && t.checkExpect(new Cell(new Trophy("r"), new BlankGround()).isImmovable(), false)
        && t.checkExpect(new Cell(new Player(">"), new BlankGround()).isImmovable(), false)
        && t.checkExpect(new Cell(new Hole(), new BlankGround()).isImmovable(), true);
  }

  // test for hasContentThatCanBePushed in cell class
  boolean testHasContentThatCanBePushed(Tester t) {
    return t.checkExpect(
        new Cell(new BlankContent(), new BlankGround()).hasContentThatCanBePushed(), false)
        && t.checkExpect(new Cell(new Wall(), new BlankGround()).hasContentThatCanBePushed(), false)
        && t.checkExpect(new Cell(new Box(), new BlankGround()).hasContentThatCanBePushed(), true)
        && t.checkExpect(new Cell(new Trophy("r"), new BlankGround()).hasContentThatCanBePushed(),
            true)
        && t.checkExpect(new Cell(new Player(">"), new BlankGround()).hasContentThatCanBePushed(),
            false)
        && t.checkExpect(new Cell(new Hole(), new BlankGround()).hasContentThatCanBePushed(),
            false);
  }

  // test for CanBePushed in Content class
  boolean testCanBePushed(Tester t) {
    return t.checkExpect(new BlankContent().canBePushed(), false)
        && t.checkExpect(new Wall().canBePushed(), false)
        && t.checkExpect(new Box().canBePushed(), true)
        && t.checkExpect(new Trophy("r").canBePushed(), true)
        && t.checkExpect(new Player(">").canBePushed(), false)
        && t.checkExpect(new Hole().canBePushed(), false);
  }

  // test for hasContentThatDestroys in cell class
  boolean testHasContentThatDestroys(Tester t) {
    return t.checkExpect(new Cell(new BlankContent(), new BlankGround()).hasContentThatDestroys(),
        false)
        && t.checkExpect(new Cell(new Wall(), new BlankGround()).hasContentThatDestroys(), false)
        && t.checkExpect(new Cell(new Box(), new BlankGround()).hasContentThatDestroys(), false)
        && t.checkExpect(new Cell(new Trophy("r"), new BlankGround()).hasContentThatDestroys(),
            false)
        && t.checkExpect(new Cell(new Player(">"), new BlankGround()).hasContentThatDestroys(),
            false)
        && t.checkExpect(new Cell(new Hole(), new BlankGround()).hasContentThatDestroys(), true);
  }

  // test for destroysContent in Content class
  boolean testDestroys(Tester t) {
    return t.checkExpect(new BlankContent().destroysContent(), false)
        && t.checkExpect(new Wall().destroysContent(), false)
        && t.checkExpect(new Box().destroysContent(), false)
        && t.checkExpect(new Trophy("r").destroysContent(), false)
        && t.checkExpect(new Player(">").destroysContent(), false)
        && t.checkExpect(new Hole().destroysContent(), true);
  }

  // test for CanBeMovedOnto in Content cell
  boolean testHasCanBeMovedOnto(Tester t) {
    return t.checkExpect(new BlankContent().canBeMovedOnto(), true)
        && t.checkExpect(new Wall().canBeMovedOnto(), false)
        && t.checkExpect(new Box().canBeMovedOnto(), false)
        && t.checkExpect(new Trophy("r").canBeMovedOnto(), false)
        && t.checkExpect(new Player(">").canBeMovedOnto(), false)
        && t.checkExpect(new Hole().canBeMovedOnto(), false);
  }

  // Tests the undo function with a few example boards
  boolean testUndo(Tester t) {
    String exampleSmallGround = "____\n" + "____\n" + "____\n" + "____";
    String exampleSmallContents = "WWWW\n" + "W_>W\n" + "W__W\n" + "WWWW";

    Board smallBoard = new Board(exampleSmallContents, exampleSmallGround);

    Board smallBoard1Move = smallBoard.movePlayerDown();
    Board smallBoard2Move = smallBoard.movePlayerLeft();

    ArrayList<Board> moveHistory2 = new ArrayList<Board>();
    moveHistory2.add(smallBoard);
    moveHistory2.add(smallBoard1Move);

    ArrayList<Board> moveHistory1 = new ArrayList<Board>();
    moveHistory1.add(smallBoard);
    PlayLevel twoMoves = new PlayLevel(smallBoard2Move, moveHistory2, 2);
    PlayLevel twoMovesUndo = new PlayLevel(smallBoard1Move, moveHistory2, 3);
    PlayLevel oneMoves = new PlayLevel(smallBoard1Move, moveHistory1, 1);
    PlayLevel oneMoveUndo = new PlayLevel(smallBoard, new ArrayList<Board>(), 2);
    PlayLevel zeroMoves = new PlayLevel(smallBoard, new ArrayList<Board>(), 0);

    return t.checkExpect(zeroMoves.undo(), zeroMoves) && t.checkExpect(oneMoves.undo(), oneMoveUndo)
        && t.checkExpect(twoMoves.undo(), twoMovesUndo);

  }

  // test for bigbang
  boolean testBigBang(Tester t) {
    PlayLevel w = new PlayLevel(levelWithIce);
    int worldWidth = 800;
    int worldHeight = 800;
    double tickRate = 0.016;

    return w.bigBang(worldWidth, worldHeight, tickRate);
  }
}