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
  buttonDisabled: boolean;

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

    const file = formData.get('file') as File;
    const id = formData.get('id') as string; 

    this.mapService.createMap(id, file).subscribe(
      () => {
        this.router.navigate(['/maps']);
      },
      error => {
        if (error.status === 403) {
        }
      }
    );
    this.buttonDisabled = true;
}

  prepareFormData(mapForm: NgForm) {
    const formData = new FormData();
    const map = mapForm.value;

    const mapNameId = map.MapNameId;

    formData.append('id', mapNameId)

    formData.append(
        'file',
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

  // removeImage(i: number, mapForm: NgForm){
  //   // this.buttonDisabled = true;
  //   console.log(mapForm)
  //   this.mapImage.splice(i, 1);
  //   mapForm.controls['file'].reset();
  //   mapForm.resetForm();
  //   this.buttonDisabled = false;
  //   // window.location.reload();
  // }
}
