package app.classmanagementappclient.Backend.Logic.CredentialValidation;

import app.classmanagementappclient.Backend.Objects.Teacher.Teacher;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author BEN
 */
public class Validation 
{
    private String errorMsg;
    
    public boolean validateTeacherCredentials (Teacher teacher) // this method can be removed, and we remain by using the 3 methods below
    {
        boolean nameValidity = isNameValid(teacher.getName());
        if (!nameValidity)
            return nameValidity;
        
        boolean isPasswordValid = isPinValid(teacher.getPin());
        if (!isPasswordValid)
            return isPasswordValid;
        
        boolean isPhoneNumberValid = isPhoneNumberValid(teacher.getPhoneNumber());
        if (!isPhoneNumberValid)
            return isPhoneNumberValid;
        
        return true;
    }
    
    public boolean isNameValid (String name)
    {   
        if (!validateCapitalLetter(name)) {
            return false;
        }        
        
        Pattern minLengthWordPattern = getMinLengthWordPattern(5);
        Pattern alphabeticWordPattern = getAlphabeticWordPattern();
        
        Map <Pattern, String> patternToErrMsgMap = new LinkedHashMap<>();
        patternToErrMsgMap.put(minLengthWordPattern, "A name must be 5 characters long");
        patternToErrMsgMap.put(alphabeticWordPattern, "The name must be of alphabetic characters only");
        
        return validateFullString(name, patternToErrMsgMap, "Your name: ");
    }
    
    public boolean isPinValid (String pin)
    {
        Pattern pattern = Pattern.compile("[\\d]{5}");
        
        Map <Pattern, String> patternToErrMsgMap = new LinkedHashMap<>();
        patternToErrMsgMap.put(pattern, "A pin must be composed of 5 digits");
     
        return validateFullString(pin, patternToErrMsgMap, "Your pin: ");
    }
    
    
    public boolean isPhoneNumberValid(String phoneNumber) {
        
        Pattern pattern = Pattern.compile("^[+][\\d]{12}");
        
        Map <Pattern, String> patternToErrMsgMap = new LinkedHashMap<>();
        patternToErrMsgMap.put(pattern, "A phone number must be composed of country code(+250) and the 9 following digits.");
     
        return validateFullString(phoneNumber, patternToErrMsgMap, "Your phone number: ");        
    }
    
    
    public boolean isEmailValid(String email) {
        
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        
        Map <Pattern, String> patternToErrMsgMap = new LinkedHashMap<>();
        patternToErrMsgMap.put(pattern, "\nInvalid email provided");
     
        return validateFullString(email, patternToErrMsgMap, "Your email: ");        
    }    
    
    
    public boolean isStudentIDValid (String studentID) // u must validate user inputs here
    {   
        Pattern pattern = Pattern.compile("^[ A-Za-z\\\\/0-9]+$");
        
        Map <Pattern, String> patternToErrMsgMap = new LinkedHashMap<>();
        patternToErrMsgMap.put(pattern, "The student ID must be composed with alphabetes(A-Z), characters('\', '/') and numbers only");
     
        return validateFullString(studentID, patternToErrMsgMap, "Your student's id: ");
    }
    
    public boolean isClassNameValid (String className)
    {
        if (!validateCapitalLetter(className)) {
            return false;
        }
        
        Pattern minLengthWordPattern = getMinLengthWordPattern(5);
        Pattern wordContent = Pattern.compile("[ A-Za-z0-9]+");
        
        Map <Pattern, String> patternToErrMsgMap = new LinkedHashMap<>();
        patternToErrMsgMap.put(minLengthWordPattern, "A class name must be composed of 5 characters long.");
        patternToErrMsgMap.put(wordContent, "A class name must only be composed of alphabetic characters and digits.");
        
        return validateFullString(className, patternToErrMsgMap, "Your classname: ");
    }
    
    public boolean isLessonNameValid (String lessonName)
    {
        if (!validateCapitalLetter(lessonName)) {
            return false;
        }        
        
        Pattern minLengthWordPattern = getMinLengthWordPattern(4, true);   
        Pattern wordContent = Pattern.compile("[ A-Za-z()]+");
        
        Map <Pattern, String> patternToErrMsgMap = new LinkedHashMap<>();
        patternToErrMsgMap.put(minLengthWordPattern, "A lesson name must be composed of 4 characters long.");
        patternToErrMsgMap.put(wordContent, "A lesson name must only be composed of alphabetic characters and curly brackets if u want.");
        
        return validateFullString(lessonName, patternToErrMsgMap, "Your lesson name: ");
    }
    
    public boolean isAssessmentNameValid (String assessmentName)
    {
        if (!validateCapitalLetter(assessmentName)) {
            return false;
        }        
        
        Pattern minLengthWordPattern = getMinLengthWordPattern(3, true);   
        Pattern wordContent = Pattern.compile("[ 0-9A-Za-z()]+");
        
        Map <Pattern, String> patternToErrMsgMap = new LinkedHashMap<>();
        patternToErrMsgMap.put(minLengthWordPattern, "An assessment name must be composed of 3 characters long.");
        patternToErrMsgMap.put(wordContent, "An assessemnt name must only be composed of alphabetic characters, numbers\nand curly brackets if u want.");
        
        return validateFullString(assessmentName, patternToErrMsgMap, "Your assessment name: ");
    }    
    
    public boolean isTermValid (int term) 
    {
        if (term < 1) {
            setErrorMsg("A term must a number greater than 0");
            return false;
        }
        return true;
    }
    
    public boolean isYearValid (int year) {
        if (year <= 1900) {
            setErrorMsg("A year must be greater than 1900!");
            return false;
        }
        return true;
    }

    // this occurs only while Editing the numbers, example term will come as "Term 3" instead of "3"
    private String numberEditChecker (String numString) 
    {
        String[] splitted = numString.split("[\s]+");
        
        if (splitted.length > 1) {
            numString = splitted[1];
        }
        return numString;
    }
    
    private boolean numberValidate (String numStr) 
    {
        if (Integer.parseInt(numStr) <= 0) {
            setErrorMsg("A term or year should be greater than 0\nYour number: " + numStr);
            return false;
        }
        return true;
    }
    
    private boolean validateCapitalLetter (String word) 
    {
        Pattern alphabeticWordStart = getAlphabeticWordStartPattern ();
        Matcher matcher = alphabeticWordStart.matcher(word);
        boolean find = matcher.find();
        
        if (!find) {
            setErrorMsg("Any word like a name must start with capital letter!\nYour case: " + word);
        }
        return find;
    }
    
    private boolean validateFullString (String word, Map<Pattern, String> patternsToErrMsgMap, String wordDescription)
    {
        StringBuilder builder = new StringBuilder();
        boolean bool = true;
//        builder.append("<html><p>");
        
        if (word.isBlank()) {
            builder.append("The input field is empty!");
        }
        
        for (Entry<Pattern, String> set : patternsToErrMsgMap.entrySet()) 
        {
            String pattern = set.getKey().pattern();
            String errMsg = set.getValue();
            
            boolean matches = word.matches (pattern);
            
            if (bool && !matches) {
                bool = matches;
            }
            
            if (!matches) {
                builder.append(errMsg).append("\n");
            }
        }
        
        if (!builder.isEmpty()) {
            setErrorMsg(builder.toString() + wordDescription + word);
        }
        
        return bool;
    }
    
    public boolean validateStudent_TotalMarks (double studentMarks, int totalMarks) {
        boolean isValid = studentMarks <= totalMarks;
        if (!isValid) {
            setErrorMsg("Student marks should be less than total marks!");
        }
        return isValid;
    }
    
    public boolean validateTotalMarks (int totalMarks) {
        boolean isValid = (totalMarks > 0);
        if (!isValid) {
            setErrorMsg("Total marks should be greater than 0!");
        }
        return isValid;
    }
    
    public boolean validateTotalMarks (String totalMarks) {
        
        boolean isValid = true;
        try {
            Integer.parseInt(totalMarks);
        }
        catch (Exception e) {
            isValid = false;
        }
        
        if (!isValid) {
            setErrorMsg("Total marks should be integer!");
        }
        return isValid;
    }    
    
    public boolean validateStudentMarks(String studentMarks) {
        boolean isValid = true;
        try {
            Double.parseDouble(studentMarks);
        }
        catch (Exception e) {
            isValid = false;
        }
        
        if (!isValid) {
            setErrorMsg("Number format error!\nNumber: " + studentMarks);
        }
        return isValid;        
    }    
    
    // example student names, teacher name ...
    private Pattern getAlphabeticWordStartPattern () {
        return Pattern.compile("^[A-Z]");
    }
    
    private Pattern getAlphabeticWordPattern () {
        return Pattern.compile("[ A-Za-z]+");
    }
    
    private Pattern getMinLengthWordPattern (int minLength, boolean...brackets) {
        return (brackets.length == 0) ? Pattern.compile("[\\w ]{"+ minLength +",}") : Pattern.compile("[\\w() ]{"+ minLength +",}");
    }
    
    private void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public String getErrorMsg() {
        return errorMsg;
    }
}
