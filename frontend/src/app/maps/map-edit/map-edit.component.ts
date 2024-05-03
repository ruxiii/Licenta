import { Component } from '@angular/core';
import { MapsComponent } from '../maps.component';
import { DepartmentsComponent } from '../../departments/departments.component';
import { DepartmentsService } from '../../departments/departments.service';
import { Router } from '@angular/router'; 
import { NgForm } from '@angular/forms';
import { FileHandle } from '../../model/file-handle.model';
import { DomSanitizer } from '@angular/platform-browser';
import { MapsService } from '../maps.service';

@Component({
  selector: 'app-map-edit',
  templateUrl: './map-edit.component.html',
  styleUrl: './map-edit.component.css'
})
export class MapEditComponent {
  isDarkMode: boolean;
  somethingWrong = false;
  maps: MapsComponent = new MapsComponent(); 
  departments: DepartmentsComponent[] = [];
  departmentsArray: string[] = [];
  mapImage: FileHandle[] = [];

  constructor(
    private router: Router,
    private departmentsService: DepartmentsService,
    private sanitizer: DomSanitizer,
    private mapService: MapsService
  ) {}

  ngOnInit(): void {
    this.departmentsService.getDepartments().subscribe(departments => {
      this.departments = departments;
      this.departmentsArray = departments.map(dep => dep.departmentId);
    });
  }

  onSubmit(mapForm: NgForm) {
    const formData = this.prepareFormData(mapForm);
    const { mapNameId, mapName } = mapForm.value;
    const mapDto = { mapNameId, mapName, mapImage: formData.get('mapFile') as File }; // Adjust this according to your actual mapDto structure
    const mapFile = formData.get('mapFile') as File;
  
    this.mapService.createMap(mapDto, mapFile).subscribe(
      () => {
        this.router.navigate(['/maps']);
      },
      error => {
        if (error.status === 403) {
          // Handle 403 error
        }
      }
    );
  }

  // const mapFormData = this.prepareFormData(mapForm); ?? nsh daca e bn mapForm

  prepareFormData(mapForm: NgForm) {
    const formData = new FormData();
    const value = mapForm.value;

    formData.append(
      'mapDto',
      new Blob([JSON.stringify('mapDto')], {type: 'application/json'})
    );
    formData.append(
      'mapFile',
      this.mapImage[0].file,
      this.mapImage[0].file.name
    );
    return formData;
  }

  onFileSelected(event) {
    if(event.target.files) {
      const file = event.target.files[0];

      const fileHandle: FileHandle = {
        file: file,
        url: this.sanitizer.bypassSecurityTrustUrl(
          window.URL.createObjectURL(file)
        )
      };

      this.mapImage.push(fileHandle);
    }

  }
}
