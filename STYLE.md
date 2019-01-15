# Style guide

## Basic rules
### Naming conventions
- Class names in UpperCamelCase
- Method names in lowerCamelCase
- Variable names in lowerCamelCase
- Constant names in CONSTANT_CASE

### Whitespace
A single blank line always appears between consecutive members of a class.  
A single blank line may also appear anywhere it improves readability, for example between statements to organize the code into logical subsections. Multiple consecutive blank lines are permitted.

### Error handling
Pop-up to a user when an error occurs, do not ignore any error that occurs.

### No unnecessary logs
Do not left logs in the code for the final product.

### Comments
Block comments are indented at the same level as the surrounding code. They may be in /* ... */ style or // ... style. For multi-line /* ... */ comments, subsequent lines must start with * aligned with the * on the previous line.

### Not used code
Do not left code in your final product that is not used anymore.

### Class order
The order in which items have to be placed in a class from up to down:
- Variables
- Constructor
- Methods

### Maximal character on a line
The maximum anmount of characters on a single line is capped to 100 characters.

### File grouping
Each class should be placed in its own file. Every subcategories of files should be placed in a package.

## Links
[Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)  
[w3 Java Programming Style Guide](https://w3.cs.jmu.edu/bernstdh/web/common/policies/styleguide-java.php)   
[Twitter Java Style Guide](https://github.com/twitter/commons/blob/master/src/java/com/twitter/common/styleguide.md#variable-naming)
