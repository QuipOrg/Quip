import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MDBBootstrapModule } from '../../typescripts/free';
import { ProfileRoutingModule } from './profile-routing.module';
import { FileUploadModule } from 'ng2-file-upload';
// Local declarations
import { ProfileComponent } from '../../components/profile/profile.component';
import { SearchPipe } from 'app/pipes/search.pipe';
import { NavbarComponent } from 'app/components/navbar/navbar.component';
// import { DashboardComponent } from '../../components/dashboard/dashboard.component';
import { PostListComponent } from '../../components/post-list/post-list.component';
import { ProfileService } from '../../services/profile/profile.service';
import { PostService } from '../../services/post/post.service';
import { DashboardComponent } from '../../components/dashboard/dashboard.component';
import { AccountListComponent } from '../../components/account-list/account-list.component';

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
    SearchPipe,
    NavbarComponent,
    // DashboardComponent,
    AccountListComponent,
    PostListComponent,
    DashboardComponent
  ],
  providers: [
    ProfileService,
    PostService
  ]
})
export class ProfileModule { }
