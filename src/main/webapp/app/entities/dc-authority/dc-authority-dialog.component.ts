import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DcAuthority } from './dc-authority.model';
import { DcAuthorityPopupService } from './dc-authority-popup.service';
import { DcAuthorityService } from './dc-authority.service';
import { DcDepartment, DcDepartmentService } from '../dc-department';
import { DcRegion, DcRegionService } from '../dc-region';

@Component({
    selector: 'jhi-dc-authority-dialog',
    templateUrl: './dc-authority-dialog.component.html'
})
export class DcAuthorityDialogComponent implements OnInit {

    dcAuthority: DcAuthority;
    isSaving: boolean;

    dcdepartments: DcDepartment[];

    dcregions: DcRegion[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dcAuthorityService: DcAuthorityService,
        private dcDepartmentService: DcDepartmentService,
        private dcRegionService: DcRegionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.dcDepartmentService.query()
            .subscribe((res: HttpResponse<DcDepartment[]>) => { this.dcdepartments = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.dcRegionService.query()
            .subscribe((res: HttpResponse<DcRegion[]>) => { this.dcregions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dcAuthority.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dcAuthorityService.update(this.dcAuthority));
        } else {
            this.subscribeToSaveResponse(
                this.dcAuthorityService.create(this.dcAuthority));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DcAuthority>>) {
        result.subscribe((res: HttpResponse<DcAuthority>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DcAuthority) {
        this.eventManager.broadcast({ name: 'dcAuthorityListModification', content: 'OK'});
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

    trackDcRegionById(index: number, item: DcRegion) {
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
    selector: 'jhi-dc-authority-popup',
    template: ''
})
export class DcAuthorityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dcAuthorityPopupService: DcAuthorityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dcAuthorityPopupService
                    .open(DcAuthorityDialogComponent as Component, params['id']);
            } else {
                this.dcAuthorityPopupService
                    .open(DcAuthorityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
