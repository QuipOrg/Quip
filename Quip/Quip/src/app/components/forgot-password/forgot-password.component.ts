import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {

  forgotPassword;


  onSendRequest = function (user) {
    console.log(user);
  }

  ngOnInit() {
    this.forgotPassword = new FormGroup({
      email: new FormControl("", Validators.compose([
        Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"),
        Validators.required
      ])),

    })
  }
}
