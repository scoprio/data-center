import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DcMenu } from './dc-menu.model';
import { DcMenuService } from './dc-menu.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-dc-menu',
    templateUrl: './dc-menu.component.html'
})
export class DcMenuComponent implements OnInit, OnDestroy {
dcMenus: DcMenu[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private dcMenuService: DcMenuService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.dcMenuService.query().subscribe(
            (res: HttpResponse<DcMenu[]>) => {
                this.dcMenus = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDcMenus();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DcMenu) {
        return item.id;
    }
    registerChangeInDcMenus() {
        this.eventSubscriber = this.eventManager.subscribe('dcMenuListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
