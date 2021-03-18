import {Component, Input, OnInit} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {HttpService} from "../../../http-service/http.service";
import {LogService} from "../../../logging/log.service";

@Component({
  selector: 'app-edit-image-modal',
  templateUrl: './edit-image-modal.component.html',
  styleUrls: ['./edit-image-modal.component.css']
})
export class EditImageModalComponent implements OnInit {

  @Input()
  user: any;

  selectedFile: File = null;

  imageSrc;

  ifImageUploaded = false;

  img = new Image();

  constructor(private modalService: NgbModal, private httpService: HttpService, private logger: LogService) {
  }

  ngOnInit() {
    console.error(this.user)
    if (this.user.imageLocation) {
      this.ifImageUploaded = true;
      this.img.src = this.user.imageLocation + '?' + new Date().getTime();
      this.logger.log('Get image from server URL: ', this.img.src);
    } else {
      this.user['imageLocation'] = '';
    }
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((formData) => {
      console.info('Saving image ...');

      this.httpService.addProfileImage(this.selectedFile).subscribe((res) => {
        if (res.status === 201) {
          this.logger.info('Image saving completed successfully.' + '\n Status: ' + res.status + '\n Location: ' + res.headers.get('location'));

          this.ifImageUploaded = true;

          this.user.imageLocation = res.headers.get('location');

          this.img.src = this.user.imageLocation + '?' + new Date().getTime();

          console.error(this.img.src)

        } else {
          this.logger.error('response body: ' + res.body + '\n status: ' + res.status);
        }
      });
    }, (reason) => {
      // jesli plik nigdy nie byl ustawiany to wyczysc w przeciwnym wypadku zostaw poprzednie zdjecie
      this.logger.info(reason);
      // if(!this.ifImageUploaded) {
      //   this.imageSrc = null;
      // }
    });
  }

  uploadFile(event) {
    this.selectedFile = <File>event.target.files[0];

    const reader = new FileReader();
    reader.onload = e => this.imageSrc = reader.result;

    reader.readAsDataURL(this.selectedFile);
  }

}
