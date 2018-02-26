import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DcRegion } from './dc-region.model';
import { DcRegionPopupService } from './dc-region-popup.service';
import { DcRegionService } from './dc-region.service';
import { DcAuthority, DcAuthorityService } from '../dc-authority';

@Component({
    selector: 'jhi-dc-region-dialog',
    templateUrl: './dc-region-dialog.component.html'
})
export class DcRegionDialogComponent implements OnInit {

    dcRegion: DcRegion;
    isSaving: boolean;

    dcauthorities: DcAuthority[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dcRegionService: DcRegionService,
        private dcAuthorityService: DcAuthorityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.dcAuthorityService.query()
            .subscribe((res: HttpResponse<DcAuthority[]>) => { this.dcauthorities = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dcRegion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dcRegionService.update(this.dcRegion));
        } else {
            this.subscribeToSaveResponse(
                this.dcRegionService.create(this.dcRegion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DcRegion>>) {
        result.subscribe((res: HttpResponse<DcRegion>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DcRegion) {
        this.eventManager.broadcast({ name: 'dcRegionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDcAuthorityById(index: number, item: DcAuthority) {
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
    selector: 'jhi-dc-region-popup',
    template: ''
})
export class DcRegionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dcRegionPopupService: DcRegionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dcRegionPopupService
                    .open(DcRegionDialogComponent as Component, params['id']);
            } else {
                this.dcRegionPopupService
                    .open(DcRegionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
