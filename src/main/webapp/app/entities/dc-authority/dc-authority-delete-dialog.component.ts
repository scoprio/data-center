import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DcAuthority } from './dc-authority.model';
import { DcAuthorityPopupService } from './dc-authority-popup.service';
import { DcAuthorityService } from './dc-authority.service';

@Component({
    selector: 'jhi-dc-authority-delete-dialog',
    templateUrl: './dc-authority-delete-dialog.component.html'
})
export class DcAuthorityDeleteDialogComponent {

    dcAuthority: DcAuthority;

    constructor(
        private dcAuthorityService: DcAuthorityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dcAuthorityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dcAuthorityListModification',
                content: 'Deleted an dcAuthority'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dc-authority-delete-popup',
    template: ''
})
export class DcAuthorityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dcAuthorityPopupService: DcAuthorityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dcAuthorityPopupService
                .open(DcAuthorityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
