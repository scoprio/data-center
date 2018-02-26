import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DcMenu } from './dc-menu.model';
import { DcMenuPopupService } from './dc-menu-popup.service';
import { DcMenuService } from './dc-menu.service';
import { DcDepartment, DcDepartmentService } from '../dc-department';

@Component({
    selector: 'jhi-dc-menu-dialog',
    templateUrl: './dc-menu-dialog.component.html'
})
export class DcMenuDialogComponent implements OnInit {

    dcMenu: DcMenu;
    isSaving: boolean;

    dcdepartments: DcDepartment[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dcMenuService: DcMenuService,
        private dcDepartmentService: DcDepartmentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.dcDepartmentService.query()
            .subscribe((res: HttpResponse<DcDepartment[]>) => { this.dcdepartments = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dcMenu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dcMenuService.update(this.dcMenu));
        } else {
            this.subscribeToSaveResponse(
                this.dcMenuService.create(this.dcMenu));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DcMenu>>) {
        result.subscribe((res: HttpResponse<DcMenu>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DcMenu) {
        this.eventManager.broadcast({ name: 'dcMenuListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDcDepartmentById(index: number, item: DcDepartment) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-dc-menu-popup',
    template: ''
})
export class DcMenuPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dcMenuPopupService: DcMenuPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dcMenuPopupService
                    .open(DcMenuDialogComponent as Component, params['id']);
            } else {
                this.dcMenuPopupService
                    .open(DcMenuDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
