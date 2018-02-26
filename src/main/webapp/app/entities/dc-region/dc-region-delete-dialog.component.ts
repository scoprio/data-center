import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DcRegion } from './dc-region.model';
import { DcRegionPopupService } from './dc-region-popup.service';
import { DcRegionService } from './dc-region.service';

@Component({
    selector: 'jhi-dc-region-delete-dialog',
    templateUrl: './dc-region-delete-dialog.component.html'
})
export class DcRegionDeleteDialogComponent {

    dcRegion: DcRegion;

    constructor(
        private dcRegionService: DcRegionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dcRegionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dcRegionListModification',
                content: 'Deleted an dcRegion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dc-region-delete-popup',
    template: ''
})
export class DcRegionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dcRegionPopupService: DcRegionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dcRegionPopupService
                .open(DcRegionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
