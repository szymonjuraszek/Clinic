import {Directive, Input} from "@angular/core";
import {AbstractControl, NG_VALIDATORS, ValidationErrors, Validator} from "@angular/forms";

@Directive({
  selector: '[passwordValidator]',
  providers: [{provide: NG_VALIDATORS, useExisting: PasswordValidatorDirective, multi: true}]
})
export class PasswordValidatorDirective implements Validator {

  validate(control: AbstractControl): ValidationErrors | null {
    if (control.value === null || control.value.length < 3) {
      return control;
    } else {
      return null;
    }
  }
}
