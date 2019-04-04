import {Component, OnInit, ChangeDetectorRef} from '@angular/core';
import {RideListService} from './ride-list.service';
import {Ride} from './ride';
import {Observable} from 'rxjs/Observable';
import { GeocodeService } from './geocode.service';
import { Location } from './location-model';

@Component({
  selector: 'ride-list-component',
  templateUrl: 'ride-list.component.html',
  styleUrls: ['./ride-list.component.scss'],
  providers: [],
})

export class RideListComponent implements OnInit {
  // public so that tests can reference them (.spec.ts)
  public rides: Ride[];

  address = 'Morris';
  location: Location;
  loading: boolean;


  // Inject the RideListService into this component.
  constructor(public rideListService: RideListService, private geocodeService: GeocodeService, private ref: ChangeDetectorRef,) {
 //   rideListService.addListener(this);
  }

  /**
   * Starts an asynchronous operation to update the rides list
   *
   */
  refreshRides(): Observable<Ride[]> {
    // Get Rides returns an Observable, basically a "promise" that
    // we will get the data from the server.
    //
    // Subscribe waits until the data is fully downloaded, then
    // performs an action on it (the first lambda)
    const rides: Observable<Ride[]> = this.rideListService.getRides();
    rides.subscribe(
      rides => {
        this.rides = rides;
      },
      err => {
        console.log(err);
      });
    return rides;
  }

  loadService(): void {
    this.rideListService.getRides().subscribe(
      rides => {
        this.rides = rides;
      },
      err => {
        console.log(err);
      }
    );
  }

  ngOnInit(): void {
    this.refreshRides();
    this.loadService();
    this.showLocation();
  }

  showLocation() {
    this.addressToCoordinates();
  }

  addressToCoordinates() {
    this.loading = true;
    this.geocodeService.geocodeAddress(this.address)
      .subscribe((location: Location) => {
          this.location = location;
          this.loading = false;
          this.ref.detectChanges();
        }
      );
  }

}
