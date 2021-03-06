import {NgModule, NO_ERRORS_SCHEMA} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {HttpClientModule} from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {UserComponent} from './users/user.component';

import {RideListComponent} from "./rides/ride-list.component";
import {UserListComponent} from './users/user-list.component';

import {UserListService} from './users/user-list.service';
import {RideListService} from './rides/ride-list.service';

import {Routing} from './app.routes';
import {APP_BASE_HREF} from '@angular/common';
import { GeocodeService } from './rides/geocode.service';

import {CustomModule} from './custom.module';
import {AgmCoreModule} from '@agm/core';

import {AddUserComponent} from './users/add-user.component';
import {AddRideComponent} from './rides/add-ride.component';



@NgModule(<NgModule>{
  imports: [
    BrowserModule,
    HttpClientModule,
    Routing,
    CustomModule,
    CommonModule,
    FormsModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyC0iaWrh4KXrN5Y06g0e3aj3QHmgXIaVJg'
    })
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    UserListComponent, RideListComponent,
    UserComponent,
    AddUserComponent, AddRideComponent,

  ],
  providers: [
    UserListService, RideListService,
    {provide: APP_BASE_HREF, useValue: '/'},
    GeocodeService,
  ],
  entryComponents: [
    AddUserComponent, AddRideComponent
  ],
  bootstrap: [AppComponent],
  schemas: [NO_ERRORS_SCHEMA]
})

export class AppModule {
}
