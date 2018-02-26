import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DcDepartment } from './dc-department.model';
import { DcDepartmentPopupService } from './dc-department-popup.service';
import { DcDepartmentService } from './dc-department.service';

@Component({
    selector: 'jhi-dc-department-delete-dialog',
    templateUrl: './dc-department-delete-dialog.component.html'
})
export class DcDepartmentDeleteDialogComponent {

    dcDepartment: DcDepartment;

    constructor(
        private dcDepartmentService: DcDepartmentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dcDepartmentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dcDepartmentListModification',
                content: 'Deleted an dcDepartment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dc-department-delete-popup',
    template: ''
})
export class DcDepartmentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dcDepartmentPopupService: DcDepartmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dcDepartmentPopupService
                .open(DcDepartmentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
