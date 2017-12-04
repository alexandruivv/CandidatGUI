package validators;

import domain.Candidate;
import exceptions.ValidatorException;

import java.util.Objects;

public class CandidateValidator implements Validator<Candidate> {

    private boolean containsLetter(String string){
        return string.matches(".*[a-zA-Z]+.*");
    }

    private boolean containsDigits(String string){
        return string.matches(".*[0-9]+.*");
    }

    private boolean validEmailAddress(String string){
        return string.matches(".*[a-zA-Z]+.*@+[a-zA-Z]+.*");
    }

    @Override
    public void validate(Candidate elem) throws ValidatorException {
        String errors = "";

        //id
        if(elem.getId() <= 0) errors += "Id has to be positive !\n";

        //first name
        if(!containsLetter(elem.getFirstName())) errors += "First name has to contain at least one letter !";

        //last name
        if(!containsLetter(elem.getLastName())) errors += "Last name has to contain at least one letter ! !\n";

        //phone number
        if(elem.getPhoneNr().length() < 5) errors += "Phone number length has to contain at least 5 digits !";
        try{
            Double.parseDouble(elem.getPhoneNr());
        }catch(NumberFormatException e){
            errors += "Phone number has to contain only digits";
        }
        if(!containsDigits(elem.getPhoneNr())) errors += "Phone number has digits !";

        //mail
        if(!validEmailAddress(elem.getMail())) errors += "Invalid mail address !";
        if(errors.length() > 0) throw new ValidatorException(errors);
    }
}
