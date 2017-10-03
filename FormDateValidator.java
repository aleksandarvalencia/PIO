package com.projects.pio.validation;

//import com.projects.trade.model.User;
//import com.projects.trade.model.FormDate;
import com.projects.pio.model.DatePicker;
import java.util.regex.Matcher;
import java.util.regex.Pattern; 
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Component("formDateValidator")
public class FormDateValidator  implements Validator{

	private Pattern pattern;
	private Matcher matcher;

	private static final String DATE_PATTERN = "\\d+(\\.\\d+)?";

        @Override
	public boolean supports(Class<?> clazz) {
		return DatePicker.class.equals(clazz);
	}

	public boolean valid(final String datum) 
        {

            matcher = pattern.matcher(datum);
            return matcher.matches();

	}
        @Override
        public void validate(Object target, Errors errors) {

		DatePicker dpicker = (DatePicker) target;


		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.datum.passwordchange");

		if(!valid(dpicker.getDay().toString())){
			errors.rejectValue("DatePicker", "datum.dan");
		}
		
		if(!valid(dpicker.getMonth().toString())){
			errors.rejectValue("DatePicker", "datum.mesec");
		}
		if(!valid(dpicker.getYear().toString())){
			errors.rejectValue("DatePicker", "datum.godina");
		}
		
		
	}

}