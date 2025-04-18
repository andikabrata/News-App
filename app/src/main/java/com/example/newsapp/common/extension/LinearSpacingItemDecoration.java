package com.example.newsapp.common.extension;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinearSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private boolean includeEdge = false;
    private final int spacing;
    private int spacingTop = 0;
    private int spacingBottom = 0;
    private int spacingRight = 0;

    public LinearSpacingItemDecoration(Context context, @DimenRes int space, boolean includeEdge) {
        this.spacing = context.getResources().getDimensionPixelSize(space);
        this.includeEdge = includeEdge;
    }

    public LinearSpacingItemDecoration(Context context, @DimenRes int space) {
        this(context, space, true);
    }

    public LinearSpacingItemDecoration(Context context,
                                       @DimenRes int space,
                                       @DimenRes int spaceTop,
                                       @DimenRes int spaceBottom,
                                       @DimenRes int spaceRight) {
        this.spacing = context.getResources().getDimensionPixelSize(space);
        this.spacingBottom = context.getResources().getDimensionPixelSize(spaceBottom);
        this.spacingTop = context.getResources().getDimensionPixelSize(spaceTop);
        this.spacingRight = context.getResources().getDimensionPixelSize(spaceRight);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        boolean isReverseLayout;
        int position = parent.getChildAdapterPosition(view); // item position
        int orientation;
        int itemCount;
        if (parent.getAdapter() == null) {
            itemCount = 0;
        } else {
            itemCount = parent.getAdapter().getItemCount();
        }

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            isReverseLayout = ((LinearLayoutManager) layoutManager).getReverseLayout();
        } else {
            throw new IllegalStateException("Please use LinearLayoutManager!");
        }

        /* IF ORIENTATION HORIZONTAL */
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            if (includeEdge) {
                outRect.top = spacing;
                outRect.bottom = spacing;
            } else {
                outRect.top = spacingTop;
                outRect.bottom = spacingBottom;
            }
            if (position == 0) {
                if (isReverseLayout) {
                    if (includeEdge) {
                        outRect.right = spacing;
                    } else {
                        outRect.right = spacingRight;
                    }
                    outRect.left = spacing / 2;
                } else {
                    if (includeEdge) {
                        outRect.left = spacing;
                    } else {
                        outRect.left = spacingRight;
                    }
                    outRect.right = spacing / 2;
                }
            } else if (position == (itemCount - 1)) {
                if (isReverseLayout) {
                    if (includeEdge) {
                        outRect.left = spacing;
                    } else {
                        outRect.left = spacingRight;
                    }
                    outRect.right = spacing / 2;
                } else {
                    if (includeEdge) {
                        outRect.right = spacing;
                    } else {
                        outRect.right = spacingRight;
                    }
                    outRect.left = spacing / 2;
                }
            } else {
                outRect.left = spacing / 2;
                outRect.right = spacing / 2;
            }
            return;
        }

        /* IF ORIENTATION VERTICAL */
        if (includeEdge) {
            outRect.left = spacing;
            outRect.right = spacing;
        } else {
            outRect.left = spacingRight;
            outRect.right = spacingRight;
        }
        if (position == 0) {
            if (isReverseLayout) {
                if (includeEdge) {
                    outRect.bottom = spacing;
                } else {
                    outRect.bottom = spacingBottom;
                }
                outRect.top = spacing / 2;
            } else {
                if (includeEdge) {
                    outRect.top = spacing;
                } else {
                    outRect.top = spacingTop;
                }
                outRect.bottom = spacing / 2;
            }
        } else if (position == (itemCount - 1)) {
            if (isReverseLayout) {
                if (includeEdge) {
                    outRect.top = spacing;
                } else {
                    outRect.top = spacingTop;
                }
                outRect.bottom = spacing / 2;
            } else {
                if (includeEdge) {
                    outRect.bottom = spacing;
                } else {
                    outRect.bottom = spacingBottom;
                }
                outRect.top = spacing / 2;
            }
        } else {
            outRect.top = spacing / 2;
            outRect.bottom = spacing / 2;
        }
    }
}
