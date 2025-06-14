import { Component } from '@angular/core';
import { MapsService } from '../maps.service';
import { MapsComponent } from '../maps.component';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ThemeService } from '../../theme-toggle/theme.service';
import { UsersService } from '../../users/users.service';

@Component({
  selector: 'app-map-list',
  templateUrl: './map-list.component.html',
  styleUrl: './map-list.component.css'
})
export class MapListComponent {
  maps: MapsComponent[];
  isDarkMode: boolean;
  private themeSubscription: Subscription;
  userRole: string; 
  selectedMap: string; 


  constructor(private mapsService: MapsService,
              private router: Router,
              private themeService: ThemeService,
              private userService: UsersService
  ) {
    this.isDarkMode = this.themeService.isDarkMode();
    this.themeSubscription = this.themeService.darkModeChanged.subscribe(isDark => {
      this.isDarkMode = isDark;
    });
  }

  ngOnInit() {
    this.mapsService.getMaps().subscribe(data => {
      this.maps = data;
    });
    this.userService.userRole$.subscribe(userRole => {
      this.userRole = userRole;
    });
  }

  ngOnDestroy() {
    this.themeSubscription.unsubscribe();
  }

  onNewMap(){
    this.router.navigate(['/maps/create']);
  }

  refactor(name: string): string{
    // console.log('name', name);
    for (let i = 0; i < name.length; i++) {
      if (name.charAt(i) === '.') {
        name = name.substring(0, i);}
    }
    return name;
  }

  onMapSelectionChange($event){
    this.selectedMap = $event.target.value;
    this.router.navigate(['/maps/' + this.selectedMap + '/availabilities']);
  }

  goBack(){
    this.router.navigate(['/welcome']);
  }
}
