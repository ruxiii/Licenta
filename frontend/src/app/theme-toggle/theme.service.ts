import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private darkModeSubject: BehaviorSubject<boolean>;
  public darkModeChanged: Observable<boolean>;

  constructor() {
    this.darkModeSubject = new BehaviorSubject<boolean>(false);
    this.darkModeChanged = this.darkModeSubject.asObservable();
  }

  isDarkMode(): boolean {
    return this.darkModeSubject.getValue();
  }

  setDarkMode(isDarkMode: boolean): void {
    this.darkModeSubject.next(isDarkMode);
    if (isDarkMode) {
      document.body.classList.add('dark-theme');
    } else {
      document.body.classList.remove('dark-theme');
    }
  }
}
