import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DcMenu } from './dc-menu.model';
import { DcMenuService } from './dc-menu.service';

@Component({
    selector: 'jhi-dc-menu-detail',
    templateUrl: './dc-menu-detail.component.html'
})
export class DcMenuDetailComponent implements OnInit, OnDestroy {

    dcMenu: DcMenu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dcMenuService: DcMenuService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDcMenus();
    }

    load(id) {
        this.dcMenuService.find(id)
            .subscribe((dcMenuResponse: HttpResponse<DcMenu>) => {
                this.dcMenu = dcMenuResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDcMenus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dcMenuListModification',
            (response) => this.load(this.dcMenu.id)
        );
    }
}
