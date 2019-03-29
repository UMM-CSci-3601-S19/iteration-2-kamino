import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Mongo-Angular-Spark lab';
  lat: number = 51.678418;
  lng: number = 7.809007;
}
