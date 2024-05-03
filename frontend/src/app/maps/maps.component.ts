import { Component } from '@angular/core';
import { FileHandle } from 'fs/promises';

@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrl: './maps.component.css'
})
export class MapsComponent {
  mapNameId: string;
  mapName: string;
  mapImage: FileHandle;
}