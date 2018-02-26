import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DcMenu } from './dc-menu.model';
import { DcMenuPopupService } from './dc-menu-popup.service';
import { DcMenuService } from './dc-menu.service';

@Component({
    selector: 'jhi-dc-menu-delete-dialog',
    templateUrl: './dc-menu-delete-dialog.component.html'
})
export class DcMenuDeleteDialogComponent {

    dcMenu: DcMenu;

    constructor(
        private dcMenuService: DcMenuService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dcMenuService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dcMenuListModification',
                content: 'Deleted an dcMenu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dc-menu-delete-popup',
    template: ''
})
export class DcMenuDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dcMenuPopupService: DcMenuPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dcMenuPopupService
                .open(DcMenuDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
