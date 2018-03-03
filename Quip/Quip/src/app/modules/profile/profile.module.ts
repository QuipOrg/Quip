import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MDBBootstrapModule } from '../../typescripts/free';
import { ProfileRoutingModule } from './profile-routing.module';
import { FileUploadModule } from 'ng2-file-upload';
// Local declarations
import { ProfileComponent } from '../../components/profile/profile.component';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { SearchPipe } from 'app/pipes/search.pipe';



@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ProfileRoutingModule,
    MDBBootstrapModule.forRoot(),
    FileUploadModule
  ],
  declarations: [
    SearchPipe,
    ProfileComponent,
    NavbarComponent,
    SearchPipe

  ]
})
export class ProfileModule { }
