import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DcAuthority } from './dc-authority.model';
import { DcAuthorityService } from './dc-authority.service';

@Injectable()
export class DcAuthorityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private dcAuthorityService: DcAuthorityService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.dcAuthorityService.find(id)
                    .subscribe((dcAuthorityResponse: HttpResponse<DcAuthority>) => {
                        const dcAuthority: DcAuthority = dcAuthorityResponse.body;
                        this.ngbModalRef = this.dcAuthorityModalRef(component, dcAuthority);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.dcAuthorityModalRef(component, new DcAuthority());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    dcAuthorityModalRef(component: Component, dcAuthority: DcAuthority): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dcAuthority = dcAuthority;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
