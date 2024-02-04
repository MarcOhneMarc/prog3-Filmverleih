# Github
## Commit-Nachrichten:
-------------------------------------------------------
#### Summary : FEAT-Film hinzufügen
#### Description : 
- Logik eingebunden
- x gefixt
-------------------------------------------------------
#### Summary : BUG-Datenbank laden
#### Description :
- gesamte Datenbank laden gefixt
- Laden mit Filter gefixt
-------------------------------------------------------
## Branch-Naming: 
- tick-xx  (xx = Kartennummer der Userstory in Trello)
-------------------------------------------------------
## Merge:
- Merge jeden Montag nach dem Sprint
- Nur kommentierter Code wird gemerged (siehe Oracle)
-------------------------------------------------------
## Konvention für Steuerelemente:
- Steuerelementname = @FXML-id
- btn_methodenName (für Steuerelemente die auf eine Methode zugreifen)
- txf_zeigtXyAn (für Steuerelemente, die nicht auf Methoden zugreifen)
-------------------------------------------------------



# Konvention für Code und Kommentare [(ausführliche Version)](https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html)
### Klassen:
Klassennamen sollten Substantive sein, in gemischter Schreibweise, wobei der erste Buchstabe jedes internen Wortes großgeschrieben wird. Versuchen Sie, die Namen Ihrer Klassen einfach und aussagekräftig zu halten. Verwenden Sie ganze Wörter – vermeiden Sie Akronyme und Abkürzungen

#### Beispiele:
```
class Film;
class FilmBibliothek;
```
-------------------------------------------------------
### Interfaces:
Interfacenamen sollten wie Klassennamen großgeschrieben werden.

#### Beispiele:
```
class Film;
class FilmBibliothek;
```
-------------------------------------------------------
### Methoden:
Methoden sollten Verben sein, in gemischter Schreibweise mit Kleinbuchstaben des ersten Buchstabens, wobei der erste Buchstabe jedes internen Wortes großgeschrieben werden muss.

#### Beispiele:
```
run();
runFast();
getBackground();
```
-------------------------------------------------------
### Variablen:
Variablennamen sollten Verben sein, in gemischter Schreibweise mit Kleinbuchstaben des ersten Buchstabens, wobei der erste Buchstabe jedes internen Wortes großgeschrieben werden muss. Variablennamen sollten nicht mit Unterstrich _ oder Dollarzeichen $ beginnen, auch wenn beides zulässig ist. Variablennamen sollten kurz, aber aussagekräftig sein. Die Wahl eines Variablennamens sollte mnemonisch sein, d. h. so gestaltet sein, dass er dem zufälligen Beobachter die Absicht seiner Verwendung anzeigt. Variablennamen mit nur einem Zeichen sollten vermieden werden, mit Ausnahme temporärer „Wegwerf“-Variablen. Gebräuchliche Namen für temporäre Variablen sind i, j, k, m und n für ganze Zahlen; c, d und e für Zeichen.

#### Beispiele:
```
int             i;
char            c;
float           myWidth;
string          myName;
```
-------------------------------------------------------
### Konstanten:
Die Namen von Variablen, die als Klassenkonstanten deklariert wurden, und von ANSI-Konstanten sollten alle in Großbuchstaben geschrieben sein, wobei die Wörter durch Unterstriche („_“) getrennt sein sollten. (ANSI-Konstanten sollten zur Vereinfachung des Debuggens vermieden werden.)

#### Beispiele:
```
static final int MIN_WIDTH = 4;
static final int MAX_WIDTH = 999;
static final int GET_THE_CPU = 1;
```
-------------------------------------------------------
#### Kommentieren der Kopfzeile:

```
/*

 * Classname
 * 
 * Version information
 *
 * Date
 * 
 * Copyright by Torvalds
 */
```

-------------------------------------------------------
#### Kommentieren von Klassen:

```
/**

 * The Example class provides ...
 */
public class Example { ...
```

-------------------------------------------------------
#### Kommentieren von Methoden:

```
/**

 * The Method does ...
 *
 * @param  ...
 * @return ... as a string
 */
public string Example { ...
```

-------------------------------------------------------
#### Kommentieren von Variablen:

```
int level; // indentation level
int size; // size of table
int level; // indentation level
int size; // size of table
Object  currentEntry; // currently selected table entry
```
























