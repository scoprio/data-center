import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DcDepartment } from './dc-department.model';
import { DcDepartmentService } from './dc-department.service';

@Injectable()
export class DcDepartmentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private dcDepartmentService: DcDepartmentService

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
                this.dcDepartmentService.find(id)
                    .subscribe((dcDepartmentResponse: HttpResponse<DcDepartment>) => {
                        const dcDepartment: DcDepartment = dcDepartmentResponse.body;
                        this.ngbModalRef = this.dcDepartmentModalRef(component, dcDepartment);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.dcDepartmentModalRef(component, new DcDepartment());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    dcDepartmentModalRef(component: Component, dcDepartment: DcDepartment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dcDepartment = dcDepartment;
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
