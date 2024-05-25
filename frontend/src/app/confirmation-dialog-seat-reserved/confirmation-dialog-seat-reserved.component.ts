import { Component } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Inject } from '@angular/core';

@Component({
  selector: 'app-confirmation-dialog-seat-reserved',
  templateUrl: './confirmation-dialog-seat-reserved.component.html',
  styleUrl: './confirmation-dialog-seat-reserved.component.css'
})
export class ConfirmationDialogSeatReservedComponent {
    constructor(
    public dialogRef: MatDialogRef<ConfirmationDialogSeatReservedComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
    ) {}

    onNoClick(): void {
      this.dialogRef.close('no');
    }
  
    onYesClick(): void {
      this.dialogRef.close('yes');
    }
}
