import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-user-detail-popup',
  templateUrl: './user-detail-popup.component.html',
  styleUrls: ['./user-detail-popup.component.css'] // Note: Corrected from styleUrl to styleUrls
})
export class UserDetailPopupComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
  }
}
